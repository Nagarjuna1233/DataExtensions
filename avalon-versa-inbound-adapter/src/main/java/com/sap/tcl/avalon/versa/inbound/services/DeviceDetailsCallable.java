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
import com.sap.tcl.avalon.versa.inbound.dtos.DeviceDetailsAsset;
import com.sap.tcl.avalon.versa.inbound.dtos.DeviceDetailsResponse;

import reactor.util.CollectionUtils;

public class DeviceDetailsCallable implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceDetailsCallable.class);
    private String deviceDetailsUrl;
    private VersaApi versaApi;
    private MessageChannel rawFragmentMessageInputChannel;
    private String dirId;

    public DeviceDetailsCallable(String deviceDetailsUrl, VersaApi versaApi,
            MessageChannel rawFragmentMessageInputChannel, String dirId) {
        super();
        this.deviceDetailsUrl = deviceDetailsUrl;
        this.versaApi = versaApi;
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
        this.dirId = dirId;
    }

    @Override
    public Integer call() throws Exception {
        if (getDeviceDetailsData()) {
            return 1;
        }
        return 0;
    }

    public boolean getDeviceDetailsData()  {

        String reString = null;
        boolean isCompleted = false;
        try {
            reString = versaApi.versaGetCall(deviceDetailsUrl, new HashMap<String, String>(), String.class);
            LOG.info(reString);
        } catch (Exception e) {
            LOG.error("Error While Pulling {}  data,cause is {}", VersaIntegrationConstants.DEVICE_DETAILS_RAW,
                    e);
            isCompleted = true;
        }
        if (StringUtils.isNotBlank(reString)) {
            DeviceDetailsResponse deviceDetailsResponse = null;

            try {
                deviceDetailsResponse = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                        DeviceDetailsResponse.class);
            } catch (Exception e) {
                LOG.error("Error While Conversion {} data,cause is {} ", VersaIntegrationConstants.DEVICE_DETAILS_RAW,
                        e);
                isCompleted = false;
            }
            if (deviceDetailsResponse != null && !CollectionUtils.isEmpty(deviceDetailsResponse.getAsset())) {
                Map<String, String> eachRecord = null;
                List<Map<String, String>> response = new ArrayList<>();
                for (DeviceDetailsAsset deviceDetails : deviceDetailsResponse.getAsset()) {
                    eachRecord = new LinkedHashMap<>();
                    eachRecord.put("asset_name", deviceDetails.getName());
                    eachRecord.put("asset_organization", deviceDetails.getOrganization());
                    eachRecord.put("asset_serial-no", deviceDetails.getSerialNo());
                    eachRecord.put("asset_status", deviceDetails.getStatus());
                    eachRecord.put("asset_location", deviceDetails.getLocation());
                    eachRecord.put("asset_latitude", deviceDetails.getLatitude());
                    eachRecord.put("asset_longitude", deviceDetails.getLongitude());
                    eachRecord.put("asset_altitude", deviceDetails.getAltitude());
                    eachRecord.put("asset_site-name", deviceDetails.getSiteName());
                    eachRecord.put("asset_site-id", String.valueOf(deviceDetails.getSiteId()));
                    eachRecord.put("asset_device-type", deviceDetails.getDeviceType());
                    eachRecord.put("asset_dir_id", this.dirId);
                    eachRecord.put("uid", this.dirId + "_" + deviceDetails.getOrganization() + "_"
                            + deviceDetails.getName() + "_" + deviceDetails.getSerialNo());
                    response.add(eachRecord);
                }
                isCompleted = this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(
                        response, AvalonUtils.constructMessageHeader(VersaIntegrationConstants.DEVICE_DETAILS_RAW,
                                this.dirId + "_" + VersaIntegrationConstants.DEVICE_DETAILS_FEED)));
            }
        } else {
            LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, deviceDetailsUrl);
            isCompleted = true;
        }
        return isCompleted;
    }
}
