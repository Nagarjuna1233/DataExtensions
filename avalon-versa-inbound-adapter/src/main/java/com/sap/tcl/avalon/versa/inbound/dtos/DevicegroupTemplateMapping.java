package com.sap.tcl.avalon.versa.inbound.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DevicegroupTemplateMapping {

    @JsonProperty("template")
    private String template;

    @JsonProperty("device-group")
    private List<String> deviceGroup = null;

    @JsonProperty("template")
    public String getTemplate() {
        return template;
    }

    @JsonProperty("template")
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty("device-group")
    public List<String> getDeviceGroup() {
        return deviceGroup;
    }

    @JsonProperty("device-group")
    public void setDeviceGroup(List<String> deviceGroup) {
        this.deviceGroup = deviceGroup;
    }

}