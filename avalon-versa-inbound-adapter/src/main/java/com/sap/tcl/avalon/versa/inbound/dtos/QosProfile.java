package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "name", "description", "peak-pps-rate", "peak-kbps-rate", "peak-burst-size", "forwarding-class",
        "loss-priority", "dscp-rw-enable", "dot1p-rw-enable" })
public class QosProfile implements Serializable {

    private static final long serialVersionUID = 4094872177240820053L;

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("peak-pps-rate")
    private String peakPpsRate;
    @JsonProperty("peak-kbps-rate")
    private String peakKbpsRate;
    @JsonProperty("peak-burst-size")
    private String peakBurstSize;
    @JsonProperty("forwarding-class")
    private String forwardingClass;
    @JsonProperty("loss-priority")
    private String lossPriority;
    @JsonProperty("dscp-rw-enable")
    private String dscpRwEnable;
    @JsonProperty("dot1p-rw-enable")
    private String dot1pRwEnable;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("peak-pps-rate")
    public String getPeakPpsRate() {
        return peakPpsRate;
    }

    @JsonProperty("peak-pps-rate")
    public void setPeakPpsRate(String peakPpsRate) {
        this.peakPpsRate = peakPpsRate;
    }

    @JsonProperty("peak-kbps-rate")
    public String getPeakKbpsRate() {
        return peakKbpsRate;
    }

    @JsonProperty("peak-kbps-rate")
    public void setPeakKbpsRate(String peakKbpsRate) {
        this.peakKbpsRate = peakKbpsRate;
    }

    @JsonProperty("peak-burst-size")
    public String getPeakBurstSize() {
        return peakBurstSize;
    }

    @JsonProperty("peak-burst-size")
    public void setPeakBurstSize(String peakBurstSize) {
        this.peakBurstSize = peakBurstSize;
    }

    @JsonProperty("forwarding-class")
    public String getForwardingClass() {
        return forwardingClass;
    }

    @JsonProperty("forwarding-class")
    public void setForwardingClass(String forwardingClass) {
        this.forwardingClass = forwardingClass;
    }

    @JsonProperty("loss-priority")
    public String getLossPriority() {
        return lossPriority;
    }

    @JsonProperty("loss-priority")
    public void setLossPriority(String lossPriority) {
        this.lossPriority = lossPriority;
    }

    @JsonProperty("dscp-rw-enable")
    public String getDscpRwEnable() {
        return dscpRwEnable;
    }

    @JsonProperty("dscp-rw-enable")
    public void setDscpRwEnable(String dscpRwEnable) {
        this.dscpRwEnable = dscpRwEnable;
    }

    @JsonProperty("dot1p-rw-enable")
    public String getDot1pRwEnable() {
        return dot1pRwEnable;
    }

    @JsonProperty("dot1p-rw-enable")
    public void setDot1pRwEnable(String dot1pRwEnable) {
        this.dot1pRwEnable = dot1pRwEnable;
    }

}