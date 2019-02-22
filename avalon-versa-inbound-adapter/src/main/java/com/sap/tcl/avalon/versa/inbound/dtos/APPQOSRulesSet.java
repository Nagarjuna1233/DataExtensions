package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class APPQOSRulesSet implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("qos-profile")
    private String qosProfile;

    @JsonProperty("qos-profile")
    public String getQosProfile() {
        return qosProfile;
    }

    @JsonProperty("qos-profile")
    public void setQosProfile(String qosProfile) {
        this.qosProfile = qosProfile;
    }

}
