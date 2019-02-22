package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "zone-list" })
public class Zone implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("zone-list")
    private List<String> zoneList;

    @JsonProperty("zone-list")
    public List<String> getZoneList() {
        return zoneList;
    }

    @JsonProperty("zone-list")
    public void setZoneList(List<String> zoneList) {
        this.zoneList = zoneList;
    }

}