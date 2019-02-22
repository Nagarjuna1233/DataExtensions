package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Set implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("forwarding-profile")
    private String forwardingProfile;
    @JsonProperty("action")
    private String action;

    @JsonProperty("forwarding-profile")
    public String getForwardingProfile() {
        return forwardingProfile;
    }

    @JsonProperty("forwarding-profile")
    public void setForwardingProfile(String forwardingProfile) {
        this.forwardingProfile = forwardingProfile;
    }

    @JsonProperty("action")
    public String getAction() {
        return action;
    }

    @JsonProperty("action")
    public void setAction(String action) {
        this.action = action;
    }

}