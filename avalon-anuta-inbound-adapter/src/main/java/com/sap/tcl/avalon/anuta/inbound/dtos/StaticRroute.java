package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StaticRroute implements Serializable {

    private static final long serialVersionUID = -9245743935481923L;
    @JsonProperty("route")
    private List<Route> route;

    @JsonProperty("route")
    public List<Route> getRoute() {
        return route;
    }

    @JsonProperty("route")
    public void setRoute(List<Route> route) {
        this.route = route;
    }

}