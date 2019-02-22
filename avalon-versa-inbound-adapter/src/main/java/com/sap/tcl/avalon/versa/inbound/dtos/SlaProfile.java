package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "name", "description", "latency", "loss-percentage", "delay-variation",
        "circuit-transmit-utilization", "circuit-receive-utilization" })
public class SlaProfile implements Serializable {

    private static final long serialVersionUID = -4750300202246432993L;

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("latency")
    private String latency;
    @JsonProperty("loss-percentage")
    private String lossPercentage;
    @JsonProperty("delay-variation")
    private String delayVariation;
    @JsonProperty("circuit-transmit-utilization")
    private String circuitTransmitUtilization;
    @JsonProperty("circuit-receive-utilization")
    private String circuitReceiveUtilization;

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

    @JsonProperty("latency")
    public String getLatency() {
        return latency;
    }

    @JsonProperty("latency")
    public void setLatency(String latency) {
        this.latency = latency;
    }

    @JsonProperty("loss-percentage")
    public String getLossPercentage() {
        return lossPercentage;
    }

    @JsonProperty("loss-percentage")
    public void setLossPercentage(String lossPercentage) {
        this.lossPercentage = lossPercentage;
    }

    @JsonProperty("delay-variation")
    public String getDelayVariation() {
        return delayVariation;
    }

    @JsonProperty("delay-variation")
    public void setDelayVariation(String delayVariation) {
        this.delayVariation = delayVariation;
    }

    @JsonProperty("circuit-transmit-utilization")
    public String getCircuitTransmitUtilization() {
        return circuitTransmitUtilization;
    }

    @JsonProperty("circuit-transmit-utilization")
    public void setCircuitTransmitUtilization(String circuitTransmitUtilization) {
        this.circuitTransmitUtilization = circuitTransmitUtilization;
    }

    @JsonProperty("circuit-receive-utilization")
    public String getCircuitReceiveUtilization() {
        return circuitReceiveUtilization;
    }

    @JsonProperty("circuit-receive-utilization")
    public void setCircuitReceiveUtilization(String circuitReceiveUtilization) {
        this.circuitReceiveUtilization = circuitReceiveUtilization;
    }

}