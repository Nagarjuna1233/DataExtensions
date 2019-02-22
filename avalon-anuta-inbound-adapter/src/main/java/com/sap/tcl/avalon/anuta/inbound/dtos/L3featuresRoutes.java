
package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "device-owner", "route" })
public class L3featuresRoutes implements Serializable {

    private static final long serialVersionUID = 5865244609346250317L;
    @JsonProperty("@")
    private DeviceOwner deviceOwner;
    @JsonProperty("route")
    private List<AllDevicesRoute> route;

    @JsonProperty("@")
    public DeviceOwner getDeviceOwner() {
        return deviceOwner;
    }

    @JsonProperty("@")
    public void setDeviceOwner(DeviceOwner deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

    @JsonProperty("route")
    public List<AllDevicesRoute> getRoute() {
        return route;
    }

    @JsonProperty("route")
    public void setRoute(List<AllDevicesRoute> route) {
        this.route = route;
    }
}