package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "forwarding-profile" })
public class ForwardProfileRespose implements Serializable {

    private static final long serialVersionUID = 2430829931103724972L;

    @JsonProperty("forwarding-profile")
    private List<ForwardingProfile> forwardingProfile;

    @JsonProperty("forwarding-profile")
    public List<ForwardingProfile> getForwardingProfile() {
        return forwardingProfile;
    }

    @JsonProperty("forwarding-profile")
    public void setForwardingProfile(List<ForwardingProfile> forwardingProfile) {
        this.forwardingProfile = forwardingProfile;
    }

}