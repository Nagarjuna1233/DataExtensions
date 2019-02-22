package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "totalCount", "appliances" })
public class VersanmsApplianceStatusResultResponse implements Serializable {

    private static final long serialVersionUID = -4537245103253650728L;
    @JsonProperty("totalCount")
    private Integer totalCount;
    @JsonProperty("appliances")
    private List<Appliance> appliances;

    @JsonProperty("totalCount")
    public Integer getTotalCount() {
        return totalCount;
    }

    @JsonProperty("totalCount")
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @JsonProperty("appliances")
    public List<Appliance> getAppliances() {
        return appliances;
    }

    @JsonProperty("appliances")
    public void setAppliances(List<Appliance> appliances) {
        this.appliances = appliances;
    }
}