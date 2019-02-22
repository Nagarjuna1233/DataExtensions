package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cpe implements Serializable {

    private static final long serialVersionUID = -1886080273008397277L;

    @JsonProperty("device-ip")
    private String deviceIp;
    @JsonProperty("@")
    private DeviceOwner deviceOwner;

    @JsonProperty("device-ip")
    public String getDeviceIp() {
        return deviceIp;
    }

    @JsonProperty("device-ip")
    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    @JsonProperty("@")
    public DeviceOwner getDeviceOwner() {
        return deviceOwner;
    }

    @JsonProperty("@")
    public void setDeviceOwner(DeviceOwner deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

}
