package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class APPQOSPolicyUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("app-qos-policy")
    private APPQOSPolicyUpdate appQosPolicy;

    @JsonProperty("app-qos-policy")
    public APPQOSPolicyUpdate getAppQosPolicy() {
        return appQosPolicy;
    }

    @JsonProperty("app-qos-policy")
    public void setAppQosPolicy(APPQOSPolicyUpdate appQosPolicy) {
        this.appQosPolicy = appQosPolicy;
    }
}
