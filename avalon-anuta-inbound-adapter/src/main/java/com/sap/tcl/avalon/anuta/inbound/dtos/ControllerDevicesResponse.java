
package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "controller:devices" })
public class ControllerDevicesResponse implements Serializable {

    private static final long serialVersionUID = 8417919347559912943L;
    @JsonProperty("controller:devices")
    private ControllerDevices controllerDevices;

    @JsonProperty("controller:devices")
    public ControllerDevices getControllerDevices() {
        return controllerDevices;
    }

    @JsonProperty("controller:devices")
    public void setControllerDevices(ControllerDevices controllerDevices) {
        this.controllerDevices = controllerDevices;
    }
}