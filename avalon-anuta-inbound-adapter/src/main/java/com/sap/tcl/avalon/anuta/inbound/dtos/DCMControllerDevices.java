
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
public class DCMControllerDevices implements Serializable {

    private static final long serialVersionUID = -8645556072648829505L;
    @JsonProperty("device")
    private List<DCMDevice> device;

    @JsonProperty("device")
    public List<DCMDevice> getDevice() {
        return device;
    }

    @JsonProperty("device")
    public void setDevice(List<DCMDevice> device) {
        this.device = device;
    }
}