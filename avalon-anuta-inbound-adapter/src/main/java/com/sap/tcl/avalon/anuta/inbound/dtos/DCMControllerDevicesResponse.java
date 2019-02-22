package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "controller:devices" })
public class DCMControllerDevicesResponse implements Serializable {

    private static final long serialVersionUID = 5361146934941998934L;
    @JsonProperty("controller:devices")
    private DCMControllerDevices controllerDevices;

    @JsonProperty("controller:devices")
    public DCMControllerDevices getControllerDevices() {
        return controllerDevices;
    }

    @JsonProperty("controller:devices")
    public void setControllerDevices(DCMControllerDevices controllerDevices) {
        this.controllerDevices = controllerDevices;
    }
}