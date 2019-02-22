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
import com.sap.tcl.avalon.versa.inbound.dtos.AppsApplication;
import com.sap.tcl.avalon.versa.inbound.dtos.AppsApplicationResponse;

import reactor.util.CollectionUtils;

public class PreApplicationsCallable implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(PreApplicationsCallable.class);
    private String appsUrl;
    private VersaApi versaApi;
    private MessageChannel rawFragmentMessageInputChannel;
    private String dirId;

    public PreApplicationsCallable(String appsUrl, VersaApi versaApi, MessageChannel rawFragmentMessageInputChannel,
            String dirId) {
        super();
        this.appsUrl = appsUrl;
        this.versaApi = versaApi;
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
        this.dirId = dirId;
    }

    @Override
    public Integer call() throws Exception {
        if (getPreAppsData()) {
            return 1;
        }
        return 0;
    }

    public boolean getPreAppsData()  {

        String reString = null;
        boolean isCompleted = false;
        try {
            reString = versaApi.versaGetCall(appsUrl, new HashMap<String, String>(), String.class);
            LOG.info(reString);
        } catch (Exception e) {
            LOG.error("Error While Pulling {}  data,cause is {}", VersaIntegrationConstants.APPS_RAW, e);
        }
        if (StringUtils.isNotBlank(reString)) {
            AppsApplicationResponse allApps = null;
            try {
                allApps = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString, AppsApplicationResponse.class);
            } catch (Exception e) {
                LOG.error("Error While Converting {} data,Cause is", VersaIntegrationConstants.APPS_RAW,
                        e);
            }
            if (allApps != null && !CollectionUtils.isEmpty(allApps.getApplication())) {

                Map<String, String> eachRecord = null;
                List<Map<String, String>> response = new ArrayList<>();
                for (AppsApplication app : allApps.getApplication()) {
                    eachRecord = new LinkedHashMap<>();
                    eachRecord.put("uid", this.dirId + "_" + app.getName());
                    eachRecord.put("application_name", app.getName());
                    eachRecord.put("poolName", this.dirId);
                    response.add(eachRecord);
                }
                isCompleted = this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(
                        response, AvalonUtils.constructMessageHeader(VersaIntegrationConstants.APPS_RAW,
                                this.dirId + "_" + VersaIntegrationConstants.PRE_APPLICATION_FEED)));
            }
        } else {
            LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, appsUrl);
            isCompleted = true;
        }
        return isCompleted;
    }
}
