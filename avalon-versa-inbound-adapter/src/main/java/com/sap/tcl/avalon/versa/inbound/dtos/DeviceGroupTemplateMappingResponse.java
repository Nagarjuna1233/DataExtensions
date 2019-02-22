package com.sap.tcl.avalon.versa.inbound.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceGroupTemplateMappingResponse {

    @JsonProperty("devicegroup-template-mapping")
    private List<DevicegroupTemplateMapping> devicegroupTemplateMapping = null;

    @JsonProperty("devicegroup-template-mapping")
    public List<DevicegroupTemplateMapping> getDevicegroupTemplateMapping() {
        return devicegroupTemplateMapping;
    }

    @JsonProperty("devicegroup-template-mapping")
    public void setDevicegroupTemplateMapping(List<DevicegroupTemplateMapping> devicegroupTemplateMapping) {
        this.devicegroupTemplateMapping = devicegroupTemplateMapping;
    }

}
