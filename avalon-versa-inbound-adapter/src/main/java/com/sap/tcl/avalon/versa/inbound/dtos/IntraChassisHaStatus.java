package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "ha-configured" })
public class IntraChassisHaStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("ha-configured")
    private Boolean haConfigured;

    @JsonProperty("ha-configured")
    public Boolean getHaConfigured() {
        return haConfigured;
    }

    @JsonProperty("ha-configured")
    public void setHaConfigured(Boolean haConfigured) {
        this.haConfigured = haConfigured;
    }
}