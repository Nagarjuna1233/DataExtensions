package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "dest-ip-address", "device-owner", "dest-mask", "options" })
public class AllDevicesRoute implements Serializable {

    private static final long serialVersionUID = 6319791464409167356L;

    @JsonProperty("dest-ip-address")
    private String destIpAddress;
    @JsonProperty("@")
    private DeviceOwner deviceOwner;
    @JsonProperty("dest-mask")
    private String destMask;
    @JsonProperty("options")
    private List<AllDevicesOption> options;

    @JsonProperty("dest-ip-address")
    public String getDestIpAddress() {
        return destIpAddress;
    }

    @JsonProperty("dest-ip-address")
    public void setDestIpAddress(String destIpAddress) {
        this.destIpAddress = destIpAddress;
    }

    @JsonProperty("@")
    public DeviceOwner getDeviceOwner() {
        return deviceOwner;
    }

    @JsonProperty("@")
    public void setDeviceOwner(DeviceOwner deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

    @JsonProperty("dest-mask")
    public String getDestMask() {
        return destMask;
    }

    @JsonProperty("dest-mask")
    public void setDestMask(String destMask) {
        this.destMask = destMask;
    }

    @JsonProperty("options")
    public List<AllDevicesOption> getOptions() {
        return options;
    }

    @JsonProperty("options")
    public void setOptions(List<AllDevicesOption> options) {
        this.options = options;
    }
}