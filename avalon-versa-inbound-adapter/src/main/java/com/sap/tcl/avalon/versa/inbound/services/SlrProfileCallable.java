package com.sap.tcl.avalon.versa.inbound.services;

import java.util.ArrayList;
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
import com.sap.tcl.avalon.versa.inbound.dtos.SlaProfile;
import com.sap.tcl.avalon.versa.inbound.dtos.SlaProfileRespose;
import com.sap.tcl.avalon.versa.inbound.dtos.Template;

import reactor.util.CollectionUtils;

public class SlrProfileCallable implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(AppsQosPolicyCallable.class);

    private String slrProfileUrl;
    private String feedName;
    private VersaApi versaApi;
    private MessageChannel rawFragmentMessageInputChannel;
    private List<Template> templates;
    private String dirId;

    public SlrProfileCallable(String slrProfileUrl, String feedName, VersaApi versaApi,
            MessageChannel rawFragmentMessageInputChannel, List<Template> templates, String dirId) {
        super();
        this.slrProfileUrl = slrProfileUrl;
        this.feedName = feedName;
        this.versaApi = versaApi;
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
        this.templates = templates;
        this.dirId = dirId;
    }

    public boolean slrProfileData(List<Template> templates)  {
        Map<String, String> paramKeyValues = null;
        SlaProfileRespose qosProfileRespose = null;
        List<Map<String, String>> response = null;

        String reString = null;
        boolean isCompleted = false;
        for (Template template : templates) {
            paramKeyValues = new HashMap<>(2);
            paramKeyValues.put(VersaIntegrationConstants.TEMPLATE_NAME, template.getName());
            paramKeyValues.put(VersaIntegrationConstants.ORG_NAME, template.getOrganization());

            try {
                reString = versaApi.versaGetCall(slrProfileUrl, paramKeyValues, String.class);
            } catch (Exception e) {
                LOG.error(VersaIntegrationConstants.ERROR_WHILE_PULLING, SlaProfileRespose.class.getName(),
                        VersaIntegrationConstants.SLA_PROFILE_RAW, e);
                isCompleted = true;
            }
            if (StringUtils.isNotBlank(reString)) {

                try {
                    qosProfileRespose = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                            SlaProfileRespose.class);
                } catch (Exception e) {
                    LOG.error(VersaIntegrationConstants.ERROR_WHILE_CONVERTNG,
                            VersaIntegrationConstants.SLA_PROFILE_RAW, e);
                    isCompleted = false;
                }
                if (qosProfileRespose != null && !CollectionUtils.isEmpty(qosProfileRespose.getSlaProfile())) {
                    response = new ArrayList<>();
                    getSlaProfileResponse(response, qosProfileRespose, template);
                    isCompleted = this.rawFragmentMessageInputChannel.send(
                            new GenericMessage<List<Map<String, String>>>(response, AvalonUtils.constructMessageHeader(
                                    VersaIntegrationConstants.SLA_PROFILE_RAW, dirId + "_" + feedName)));
                }
            } else {
                isCompleted = true;
                LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, slrProfileUrl);
            }
        }
        LOG.info("::::SLA PROFILES LOADING STATUS ::::::{}", isCompleted);
        return isCompleted;
    }

    @Override
    public Integer call() throws Exception {
        if (slrProfileData(templates)) {
            return 1;
        }
        return 0;
    }

    private void getSlaProfileResponse(List<Map<String, String>> response, SlaProfileRespose qosProfileRespose,
            Template template) {

        Map<String, String> eachRecord = null;
        for (SlaProfile slaProfile : qosProfileRespose.getSlaProfile()) {
            eachRecord = new LinkedHashMap<>();
            eachRecord.put("uid", this.dirId + "_" + template.getOrganization() + "_" + template.getName() + "_"
                    + slaProfile.getName());
            eachRecord.put("sla-profile_name", slaProfile.getName());
            eachRecord.put("sla-profile_description", slaProfile.getDescription());
            eachRecord.put("sla-profile_latency", slaProfile.getLatency());
            eachRecord.put("sla-profile_loss-percentage", slaProfile.getLossPercentage());
            eachRecord.put("sla-profile_delay-variation", slaProfile.getDelayVariation());
            eachRecord.put("sla-profile_circuit-transmit-utilization", slaProfile.getCircuitTransmitUtilization());
            eachRecord.put("sla-profile_circuit-receive-utilization", slaProfile.getCircuitReceiveUtilization());
            eachRecord.put("template-name", this.dirId + "_" + template.getOrganization() + "_" + template.getName());
            eachRecord.put("poolName", this.dirId + VersaIntegrationConstants.POOL);
            response.add(eachRecord);
        }
    }
}
