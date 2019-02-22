package com.sap.tcl.avalon.versa.inbound.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "sdwan-policy-group" })
public class PolicyGroupResponse {

    @JsonProperty("sdwan-policy-group")
    private List<SdwanPolicyGroup> sdwanPolicyGroup = null;

    @JsonProperty("sdwan-policy-group")
    public List<SdwanPolicyGroup> getSdwanPolicyGroup() {
        return sdwanPolicyGroup;
    }

    @JsonProperty("sdwan-policy-group")
    public void setSdwanPolicyGroup(List<SdwanPolicyGroup> sdwanPolicyGroup) {
        this.sdwanPolicyGroup = sdwanPolicyGroup;
    }

}