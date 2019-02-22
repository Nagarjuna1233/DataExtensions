package com.sap.tcl.avalon.versa.inbound.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.sap.tcl.avalon.utils.AvalonUtils;
import com.sap.tcl.avalon.versa.inbound.constants.VersaIntegrationConstants;
import com.sap.tcl.avalon.versa.inbound.dtos.CircuitPriorities;
import com.sap.tcl.avalon.versa.inbound.dtos.ForwardProfileRespose;
import com.sap.tcl.avalon.versa.inbound.dtos.ForwardingProfile;
import com.sap.tcl.avalon.versa.inbound.dtos.Priority;
import com.sap.tcl.avalon.versa.inbound.dtos.Template;

import reactor.util.CollectionUtils;

public class ForwardProfileCallable implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(ForwardProfileCallable.class);
    private String forwardProfilesUrl;
    private String feedName;
    private VersaApi versaApi;
    private MessageChannel rawFragmentMessageInputChannel;
    List<Template> templates;
    String dirId;

    public ForwardProfileCallable(String forwardProfilesUrl, String feedName, VersaApi versaApi,
            MessageChannel rawFragmentMessageInputChannel, List<Template> templates, String dirId) {
        super();
        this.forwardProfilesUrl = forwardProfilesUrl;
        this.feedName = feedName;
        this.versaApi = versaApi;
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
        this.templates = templates;
        this.dirId = dirId;
    }

    public boolean forwardProfilesData(List<Template> templates)  {
        Map<String, String> paramKeyValues = null;
        ForwardProfileRespose forwardProfileRespose = null;
        List<Map<String, String>> response = null;
        String reString = null;
        boolean isCompleted = false;
        for (Template template : templates) {
            paramKeyValues = new HashMap<>(2);
            paramKeyValues.put(VersaIntegrationConstants.TEMPLATE_NAME, template.getName());
            paramKeyValues.put(VersaIntegrationConstants.ORG_NAME, template.getOrganization());

            try {
                reString = versaApi.versaGetCall(forwardProfilesUrl, paramKeyValues, String.class);
                LOG.info(reString);
            } catch (Exception e) {
                LOG.error(VersaIntegrationConstants.ERROR_WHILE_PULLING, ForwardProfileRespose.class.getName(), e);
                isCompleted = true;
            }

            if (StringUtils.isNotBlank(reString)) {
                try {
                    forwardProfileRespose = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                            ForwardProfileRespose.class);
                } catch (Exception e) {
                    LOG.error(VersaIntegrationConstants.ERROR_WHILE_CONVERTNG, ForwardProfileRespose.class.getName(),
                            VersaIntegrationConstants.F_PROFILE_RAW, ForwardProfileRespose.class.getName(), e);
                    isCompleted = false;
                }
                if (forwardProfileRespose != null
                        && !CollectionUtils.isEmpty(forwardProfileRespose.getForwardingProfile())) {
                    response = new ArrayList<>();

                    getForwardProfilesResponse(response, forwardProfileRespose, template);
                    isCompleted = this.rawFragmentMessageInputChannel.send(
                            new GenericMessage<List<Map<String, String>>>(response, AvalonUtils.constructMessageHeader(
                                    VersaIntegrationConstants.F_PROFILE_RAW, dirId + "_" + feedName)));
                }
            } else {
                LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, forwardProfilesUrl);
                isCompleted = true;
            }
        }
        return isCompleted;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public boolean postCircuitPriorityData(String profileUid, CircuitPriorities circuitPriorities) {
        boolean isCompleted = false;
        if (null != circuitPriorities) {
            List<Map<String, String>> response = new ArrayList<>();
            List<Priority> prioroties = circuitPriorities.getPriority();
            if (prioroties != null) {
                getCircuitPriorityData(response, prioroties, profileUid);
            }
            isCompleted = this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(
                    response, AvalonUtils.constructMessageHeader(VersaIntegrationConstants.CIRCUITE_PRI,
                            dirId + "_" + VersaIntegrationConstants.CIRCUT_PRIORITY_FEED)));
        } else {
            isCompleted = true;
        }
        return isCompleted;
    }

    @Override
    public Integer call() throws Exception {
        if (forwardProfilesData(templates)) {
            return 1;
        }
        return 0;
    }

    private void getForwardProfilesResponse(List<Map<String, String>> response,
            ForwardProfileRespose forwardProfileRespose, Template template) {
        Map<String, String> eachRecord = null;
        for (ForwardingProfile forwardProfile : forwardProfileRespose.getForwardingProfile()) {
            eachRecord = new LinkedHashMap<>();
            eachRecord.put("uid", this.dirId + "_" + template.getOrganization() + "_" + template.getName() + "_"
                    + forwardProfile.getName());
            eachRecord.put("forwarding-profile_name", forwardProfile.getName());
            eachRecord.put("forwarding-profile_description", forwardProfile.getDescription());
            eachRecord.put("forwarding-profile_connection-selection-method",
                    forwardProfile.getConnectionSelectionMethod());
            eachRecord.put("forwarding-profile_sla-violation-action", forwardProfile.getSlaViolationAction());
            eachRecord.put("forwarding-profile_evaluate-continuously", forwardProfile.getEvaluateContinuously());
            eachRecord.put("forwarding-profile_encryption", forwardProfile.getEncryption());
            eachRecord.put("forwarding-profile_symmetric-forwarding", forwardProfile.getSymmetricForwarding());
            eachRecord.put("forwarding-profile_sla-profile", this.dirId + "_" + template.getOrganization() + "_"
                    + template.getName() + "_" + forwardProfile.getSlaProfile());
            eachRecord.put("definitionType", "USERDEFINED");
            eachRecord.put("template-name", this.dirId + "_" + template.getOrganization() + "_" + template.getName());
            eachRecord.put("poolName", this.dirId + "_POOL");

            response.add(eachRecord);
            postCircuitPriorityData(this.dirId + "_" + template.getOrganization() + "_" + template.getName() + "_"
                    + forwardProfile.getName(), forwardProfile.getCircuitPriorities());
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void getCircuitPriorityData(List<Map<String, String>> response, List<Priority> prioroties,
            String profileUid) {
        Map<String, String> eachRecord = null;
        for (Priority proirity : prioroties) {
            eachRecord = new LinkedHashMap<>();
            eachRecord.put("forwarding-profile_name", profileUid);
            eachRecord.put("forwarding-profile_circuit-priorities_priority_value", proirity.getValue());
            eachRecord.put("forwarding-profile_circuit-priorities_priority_description", proirity.getDescription());
            if (null != proirity.getCircuitNames()) {
                eachRecord.put("forwarding-profile_circuit-priorities_priority_circuit-names_local",
                        AvalonUtils.listToString((Collection) proirity.getCircuitNames().getLocal(), ","));
                eachRecord.put("forwarding-profile_circuit-priorities_priority_circuit-names_remote",
                        AvalonUtils.listToString((Collection) proirity.getCircuitNames().getRemote(), ","));
            }
            if (null != proirity.getCircuitTypes()) {
                eachRecord.put("forwarding-profile_circuit-priorities_priority_circuit-types_local",
                        AvalonUtils.listToString((Collection) proirity.getCircuitTypes().getLocal(), ","));
                eachRecord.put("forwarding-profile_circuit-priorities_priority_circuit-types_remote",
                        AvalonUtils.listToString((Collection) proirity.getCircuitTypes().getLocal(), ","));
            }
            if (null != proirity.getCircuitMedia()) {
                eachRecord.put("forwarding-profile_circuit-priorities_priority_circuit-media_local",
                        AvalonUtils.listToString((Collection) proirity.getCircuitMedia().getLocal(), ","));
                eachRecord.put("forwarding-profile_circuit-priorities_priority_circuit-media_remote",
                        AvalonUtils.listToString((Collection) proirity.getCircuitMedia().getLocal(), ","));
            }
            eachRecord.put("poolName", this.dirId + VersaIntegrationConstants.POOL);
            response.add(eachRecord);
        }
    }
}
