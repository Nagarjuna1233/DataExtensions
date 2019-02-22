package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "qos-profile" })
public class QosProfileRespose implements Serializable {

    private static final long serialVersionUID = -7385861781543702448L;

    @JsonProperty("qos-profile")
    private List<QosProfile> qosProfile;

    @JsonProperty("qos-profile")
    public List<QosProfile> getQosProfile() {
        return qosProfile;
    }

    @JsonProperty("qos-profile")
    public void setQosProfile(List<QosProfile> qosProfile) {
        this.qosProfile = qosProfile;
    }

}
