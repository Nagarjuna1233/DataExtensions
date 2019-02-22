package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "device" })
public class ControllerDevices implements Serializable {

    private static final long serialVersionUID = -3836627420890398115L;
    @JsonProperty("device")
    private List<Device> device;

    @JsonProperty("device")
    public List<Device> getDevice() {
        return device;
    }

    @JsonProperty("device")
    public void setDevice(List<Device> device) {
        this.device = device;
    }
}