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
import com.sap.tcl.avalon.versa.inbound.dtos.QosProfile;
import com.sap.tcl.avalon.versa.inbound.dtos.QosProfileRespose;
import com.sap.tcl.avalon.versa.inbound.dtos.Template;

import reactor.util.CollectionUtils;

public class QosForwardProfilesCallable implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(QosForwardProfilesCallable.class);

    private String qosForwardProfilesUrl;
    private String feedName;
    private VersaApi versaApi;
    private MessageChannel rawFragmentMessageInputChannel;
    private List<Template> templates;
    private String dirId;

    public QosForwardProfilesCallable(String qosForwardProfilesUrl, String feedName, VersaApi versaApi,
            MessageChannel rawFragmentMessageInputChannel, List<Template> templates, String dirId) {
        super();
        this.qosForwardProfilesUrl = qosForwardProfilesUrl;
        this.feedName = feedName;
        this.versaApi = versaApi;
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
        this.templates = templates;
        this.dirId = dirId;
    }

    public boolean qosForwardProfilesData(List<Template> templates)  {
        Map<String, String> paramKeyValues = null;
        QosProfileRespose qosProfileRespose = null;
        List<Map<String, String>> response = null;
        boolean isCompleted = false;
        String reString = null;
        for (Template template : templates) {
            paramKeyValues = new HashMap<>(2);
            paramKeyValues.put(VersaIntegrationConstants.TEMPLATE_NAME, template.getName());
            paramKeyValues.put(VersaIntegrationConstants.ORG_NAME, template.getOrganization());
            try {
                reString = versaApi.versaGetCall(qosForwardProfilesUrl, paramKeyValues, String.class);
                LOG.info(reString);
            } catch (Exception e) {
                LOG.info(VersaIntegrationConstants.ERROR_WHILE_PULLING, QosProfileRespose.class.getName(), e);
                isCompleted = true;
            }
            if (StringUtils.isNotBlank(reString)) {

                try {
                    qosProfileRespose = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                            QosProfileRespose.class);
                } catch (Exception e) {
                    LOG.error(VersaIntegrationConstants.ERROR_WHILE_CONVERTNG, QosProfileRespose.class.getName(),
                            VersaIntegrationConstants.TEMPLATE_RAW, QosProfileRespose.class.getName(), e);
                    isCompleted = false;
                }
                if (qosProfileRespose != null && !CollectionUtils.isEmpty(qosProfileRespose.getQosProfile())) {
                    response = new ArrayList<>();
                    getQosProfilesResponse(response, qosProfileRespose, template);
                    isCompleted = this.rawFragmentMessageInputChannel.send(
                            new GenericMessage<List<Map<String, String>>>(response, AvalonUtils.constructMessageHeader(
                                    VersaIntegrationConstants.APPS_QOS_F_PROFILE_RAW, dirId + "_" + feedName)));
                }
            } else {
                isCompleted = true;
                LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, qosForwardProfilesUrl);
            }
        }
        return isCompleted;
    }

    @Override
    public Integer call() throws Exception {
        if (qosForwardProfilesData(templates)) {
            return 1;
        }
        return 0;
    }

    private void getQosProfilesResponse(List<Map<String, String>> response, QosProfileRespose qosProfileRespose,
            Template template) {
        Map<String, String> eachRecord = null;
        for (QosProfile qosProfile : qosProfileRespose.getQosProfile()) {
            eachRecord = new LinkedHashMap<>();
            eachRecord.put("uid", this.dirId + "_" + template.getOrganization() + "_" + template.getName() + "_"
                    + qosProfile.getName());
            eachRecord.put("qos-profile_name", qosProfile.getName());
            eachRecord.put("qos-profile_description", qosProfile.getDescription());
            eachRecord.put("qos-profile_peak-pps-rate", qosProfile.getPeakPpsRate());
            eachRecord.put("qos-profile_peak-kbps-rate", qosProfile.getPeakKbpsRate());
            eachRecord.put("qos-profile_peak-burst-size", qosProfile.getPeakBurstSize());
            eachRecord.put("qos-profile_forwarding-class", qosProfile.getForwardingClass());
            eachRecord.put("qos-profile_loss-priority", qosProfile.getLossPriority());
            eachRecord.put("template-name", this.dirId + "_" + template.getOrganization() + "_" + template.getName());
            eachRecord.put("polName", dirId + VersaIntegrationConstants.POOL);
            response.add(eachRecord);
        }
    }
}
