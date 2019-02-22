package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "sla-profile" })
public class SlaProfileRespose implements Serializable {

    private static final long serialVersionUID = 5969293352546069872L;

    @JsonProperty("sla-profile")
    private List<SlaProfile> slaProfile;

    @JsonProperty("sla-profile")
    public List<SlaProfile> getSlaProfile() {
        return slaProfile;
    }

    @JsonProperty("sla-profile")
    public void setSlaProfile(List<SlaProfile> slaProfile) {
        this.slaProfile = slaProfile;
    }
}