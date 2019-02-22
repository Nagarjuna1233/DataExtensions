package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Route implements Serializable {

    private static final long serialVersionUID = -4512034267848592007L;

    @JsonProperty("dest-ip-address")
    private String destIpAddress;

    @JsonProperty("dest-mask")
    private String destMask;

    @JsonProperty("options")
    private List<Option> options;

    @JsonProperty("dest-ip-address")
    public String getDestIpAddress() {
        return destIpAddress;
    }

    @JsonProperty("dest-ip-address")
    public void setDestIpAddress(String destIpAddress) {
        this.destIpAddress = destIpAddress;
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
    public List<Option> getOptions() {
        return options;
    }

    @JsonProperty("options")
    public void setOptions(List<Option> options) {
        this.options = options;
    }

}