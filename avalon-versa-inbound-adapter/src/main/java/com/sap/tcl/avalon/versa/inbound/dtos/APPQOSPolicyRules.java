package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class APPQOSPolicyRules implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("app-qos-policy")
    private List<APPQOSPolicy> appQosPolicy;

    @JsonProperty("app-qos-policy")
    public List<APPQOSPolicy> getAppQosPolicy() {
        return appQosPolicy;
    }

    @JsonProperty("app-qos-policy")
    public void setAppQosPolicy(List<APPQOSPolicy> appQosPolicy) {
        this.appQosPolicy = appQosPolicy;
    }

}
