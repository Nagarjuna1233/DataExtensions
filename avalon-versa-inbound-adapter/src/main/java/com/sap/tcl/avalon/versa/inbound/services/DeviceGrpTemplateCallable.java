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
import com.sap.tcl.avalon.versa.inbound.dtos.DeviceGroupTemplateMappingResponse;
import com.sap.tcl.avalon.versa.inbound.dtos.DevicegroupTemplateMapping;
import reactor.util.CollectionUtils;

public class DeviceGrpTemplateCallable implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceGrpTemplateCallable.class);
    private String deviceGroupTemplatesUrl;
    private VersaApi versaApi;
    private MessageChannel rawFragmentMessageInputChannel;
    private String dirId;

    public DeviceGrpTemplateCallable(String deviceGroupTemplatesUrl, VersaApi versaApi,
            MessageChannel rawFragmentMessageInputChannel, String dirId) {
        super();
        this.deviceGroupTemplatesUrl = deviceGroupTemplatesUrl;
        this.versaApi = versaApi;
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
        this.dirId = dirId;
    }

    @Override
    public Integer call() throws Exception {
        if (getDeviceGrpTempData()) {
            return 1;
        }
        return 0;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public boolean getDeviceGrpTempData()  {

        String reString = null;
        boolean isCompleted = false;
        try {
            reString = versaApi.versaGetCall(deviceGroupTemplatesUrl, new HashMap<String, String>(), String.class);
            LOG.info(reString);
        } catch (Exception e) {
            LOG.error("Error While Pulling {}  data,cause is {}", VersaIntegrationConstants.DEVICE_GRP_TEMPLATE_RAW,
                    e);
        }
        if (StringUtils.isNotBlank(reString)) {
            DeviceGroupTemplateMappingResponse devicegroupTemplateMapping = null;

            try {
                devicegroupTemplateMapping = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                        DeviceGroupTemplateMappingResponse.class);
            } catch (Exception e) {
                LOG.error("Error While Converting {} data,Cause is", VersaIntegrationConstants.DEVICE_GRP_TEMPLATE_RAW,
                        e);
            }
            if (devicegroupTemplateMapping != null
                    && !CollectionUtils.isEmpty(devicegroupTemplateMapping.getDevicegroupTemplateMapping())) {

                Map<String, String> eachRecord = null;
                List<Map<String, String>> response = new ArrayList<>();
                for (DevicegroupTemplateMapping org : devicegroupTemplateMapping.getDevicegroupTemplateMapping()) {
                    eachRecord = new LinkedHashMap<>();
                    eachRecord.put("devicegroup-template-mapping_template", org.getTemplate());
                    eachRecord.put("devicegroup-template-mapping_device-group",
                            AvalonUtils.listToString((Collection) org.getDeviceGroup(), ","));
                    eachRecord.put("poolName", this.dirId + VersaIntegrationConstants.POOL);
                    response.add(eachRecord);
                }
                isCompleted = this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(
                        response, AvalonUtils.constructMessageHeader(VersaIntegrationConstants.DEVICE_GRP_TEMPLATE_RAW,
                                this.dirId + "_" + VersaIntegrationConstants.DEVICE_GRP_TEMPMAP_FEED)));
            }
        } else {
            LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, deviceGroupTemplatesUrl);
            isCompleted = true;
        }
        return isCompleted;
    }
}
