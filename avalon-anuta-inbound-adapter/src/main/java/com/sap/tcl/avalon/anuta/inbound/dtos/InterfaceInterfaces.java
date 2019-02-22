package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "@", "interface" })
public class InterfaceInterfaces implements Serializable {

    private static final long serialVersionUID = 3795005853317111932L;
    @JsonProperty("@")
    private DeviceOwner deviceOwner;
    @JsonProperty("interface")
    private List<DeviceInterface> deviceInterface;

    @JsonProperty("@")
    public DeviceOwner getDeviceOwner() {
        return deviceOwner;
    }

    @JsonProperty("device-owner")
    public void setDeviceOwner(DeviceOwner deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

    @JsonProperty("interface")
    public List<DeviceInterface> getDeviceInterface() {
        return deviceInterface;
    }

    @JsonProperty("interface")
    public void setDeviceInterface(List<DeviceInterface> deviceInterface) {
        this.deviceInterface = deviceInterface;
    }
}