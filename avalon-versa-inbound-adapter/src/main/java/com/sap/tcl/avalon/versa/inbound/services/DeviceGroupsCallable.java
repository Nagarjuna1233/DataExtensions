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
import com.sap.tcl.avalon.versa.inbound.dtos.DeviceGroup;
import com.sap.tcl.avalon.versa.inbound.dtos.DeviceGroupResonse;

import reactor.util.CollectionUtils;

public class DeviceGroupsCallable implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceGroupsCallable.class);
    private String deviceGroupUrl;
    private VersaApi versaApi;
    private MessageChannel rawFragmentMessageInputChannel;
    private String dirId;

    public DeviceGroupsCallable(String deviceGroupUrl, VersaApi versaApi, MessageChannel rawFragmentMessageInputChannel,
            String dirId) {
        super();
        this.deviceGroupUrl = deviceGroupUrl;
        this.versaApi = versaApi;
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
        this.dirId = dirId;
    }

    @Override
    public Integer call() throws Exception {
        if (getDeviceGroupsData()) {
            return 1;
        }
        return 0;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public boolean getDeviceGroupsData()  {
        String reString = null;
        boolean isCompleted = false;
        try {
            reString = versaApi.versaGetCall(deviceGroupUrl, new HashMap<String, String>(), String.class);
            LOG.info(reString);
        } catch (Exception e) {
            LOG.error("Error While pulling {} data,Cause is {}", VersaIntegrationConstants.DEVICE_GROUP_RAW,
                    e);
            isCompleted = true;
        }
        if (StringUtils.isNotBlank(reString)) {
            DeviceGroupResonse devicegroupTemplateMapping = null;
            try {
                devicegroupTemplateMapping = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                        DeviceGroupResonse.class);
            } catch (Exception e) {
                LOG.error("Error While Converting {} data,Cause is {}", VersaIntegrationConstants.DEVICE_GROUP_RAW,
                        e);
                isCompleted = false;
            }
            if (devicegroupTemplateMapping != null
                    && !CollectionUtils.isEmpty(devicegroupTemplateMapping.getDeviceGroup())) {

                Map<String, String> eachRecord = null;
                List<Map<String, String>> response = new ArrayList<>();
                for (DeviceGroup deviceGroup : devicegroupTemplateMapping.getDeviceGroup()) {
                    eachRecord = new LinkedHashMap<>();
                    eachRecord.put("uid",
                            this.dirId + "_" + deviceGroup.getOrganization() + "_" + deviceGroup.getName());

                    eachRecord.put("device-group_name", deviceGroup.getName());
                    eachRecord.put("device-group_serial-no", deviceGroup.getSerialNo());
                    eachRecord.put("device-group_inventory-name",
                            AvalonUtils.listToString((Collection) deviceGroup.getInventoryName(), ","));
                    eachRecord.put("device-group_description", deviceGroup.getDescription());
                    eachRecord.put("device-group_e-mail", deviceGroup.geteMail());
                    eachRecord.put("device-group_organization", deviceGroup.getOrganization());
                    eachRecord.put("device-group_dir_id", this.dirId);
                    response.add(eachRecord);
                }
                isCompleted = this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(
                        response, AvalonUtils.constructMessageHeader(VersaIntegrationConstants.DEVICE_GROUP_RAW,
                                this.dirId + "_" + VersaIntegrationConstants.DEVICE_GROUP_FEED)));
            }
        } else {
            LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, deviceGroupUrl);
            isCompleted = true;
        }

        return isCompleted;

    }
}
