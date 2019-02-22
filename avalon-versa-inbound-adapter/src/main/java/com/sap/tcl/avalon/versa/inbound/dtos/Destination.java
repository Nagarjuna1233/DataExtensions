package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class Destination implements Serializable {

    private static final long serialVersionUID = 8258566712813958979L;
    @JsonProperty("region")
    private List<String> region;

    @JsonProperty("region")
    public List<String> getRegion() {
        return region;
    }

    @JsonProperty("region")
    public void setRegion(List<String> region) {
        this.region = region;
    }

}