package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "device-group" })
public class DeviceGroupResonse implements Serializable {

    private static final long serialVersionUID = 6832652633291488919L;
    @JsonProperty("device-group")
    private List<DeviceGroup> deviceGroup;

    @JsonProperty("device-group")
    public List<DeviceGroup> getDeviceGroup() {
        return deviceGroup;
    }

    @JsonProperty("device-group")
    public void setDeviceGroup(List<DeviceGroup> deviceGroup) {
        this.deviceGroup = deviceGroup;
    }
}
