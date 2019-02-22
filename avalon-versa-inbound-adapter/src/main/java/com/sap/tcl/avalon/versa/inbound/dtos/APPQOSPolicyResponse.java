package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class APPQOSPolicyResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("app-qos-policy-group")
    private List<APPQOSPolicyGroup> appQosPolicyGroup;

    @JsonProperty("app-qos-policy-group")
    public List<APPQOSPolicyGroup> getAppQosPolicyGroup() {
        return appQosPolicyGroup;
    }

    @JsonProperty("app-qos-policy-group")
    public void setAppQosPolicyGroup(List<APPQOSPolicyGroup> appQosPolicyGroup) {
        this.appQosPolicyGroup = appQosPolicyGroup;
    }

}
