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
import com.sap.tcl.avalon.versa.inbound.dtos.Appliance;
import com.sap.tcl.avalon.versa.inbound.dtos.ApplianceStatusResultResopnse;

import reactor.util.CollectionUtils;

public class DeviceStatusCallable implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceStatusCallable.class);
    private String deviceStatusUrl;
    private VersaApi versaApi;
    private MessageChannel rawFragmentMessageInputChannel;
    private String dirId;

    public DeviceStatusCallable(String deviceStatusUrl, VersaApi versaApi,
            MessageChannel rawFragmentMessageInputChannel, String dirId) {
        super();
        this.deviceStatusUrl = deviceStatusUrl;
        this.versaApi = versaApi;
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
        this.dirId = dirId;
    }

    @Override
    public Integer call() throws Exception {
        if (getDeviceStarusData()) {
            return 1;
        }
        return 0;
    }

    public boolean getDeviceStarusData()  {

        String reString = null;
        boolean isCompleted = false;
        try {
            reString = versaApi.versaGetCall(deviceStatusUrl, new HashMap<String, String>(), String.class);
            LOG.info(reString);
        } catch (Exception e) {
            LOG.error("Error While Pulling {}  data,cause is {}", VersaIntegrationConstants.DEVICE_STATUS_RAW,
                    e);
        }
        if (StringUtils.isNotBlank(reString)) {
            ApplianceStatusResultResopnse deviceDetailsResponse = null;

            try {
                deviceDetailsResponse = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                        ApplianceStatusResultResopnse.class);
            } catch (Exception e) {
                LOG.error("Error While Converting {} data,Cause is", VersaIntegrationConstants.DEVICE_STATUS_RAW,
                        e);
            }
            if (deviceDetailsResponse != null && !CollectionUtils
                    .isEmpty(deviceDetailsResponse.getVersanmsApplianceStatusResult().getAppliances())) {

                Map<String, String> eachRecord = null;
                List<Map<String, String>> response = new ArrayList<>();
                for (Appliance org : deviceDetailsResponse.getVersanmsApplianceStatusResult().getAppliances()) {
                    eachRecord = new LinkedHashMap<>();
                    eachRecord.put("versanms.ApplianceStatusResult_appliances_name", org.getName());
                    eachRecord.put("versanms.ApplianceStatusResult_appliances_ping-status", org.getPingStatus());
                    eachRecord.put("poolName", this.dirId + VersaIntegrationConstants.POOL);
                    response.add(eachRecord);
                }
                isCompleted = this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(
                        response, AvalonUtils.constructMessageHeader(VersaIntegrationConstants.DEVICE_STATUS_RAW,
                                this.dirId + "_" + VersaIntegrationConstants.DEVICE_STATUS_FEED)));
            }
        } else {
            LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, deviceStatusUrl);
            isCompleted = true;
        }
        return isCompleted;

    }
}
