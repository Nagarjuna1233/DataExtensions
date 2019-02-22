package com.sap.tcl.avalon.versa.inbound.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDetailsResponse {

    @JsonProperty("asset")
    private List<DeviceDetailsAsset> asset = null;

    @JsonProperty("asset")
    public List<DeviceDetailsAsset> getAsset() {
        return asset;
    }

    @JsonProperty("asset")
    public void setAsset(List<DeviceDetailsAsset> asset) {
        this.asset = asset;
    }

}
