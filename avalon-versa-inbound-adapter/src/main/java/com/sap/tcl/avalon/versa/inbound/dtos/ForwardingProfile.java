package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "name", "description", "sla-profile", "circuit-priorities", "connection-selection-method",
        "sla-violation-action", "evaluate-continuously", "recompute-timer", "encryption", "symmetric-forwarding",
        "load-balance" })
public class ForwardingProfile implements Serializable {

    private static final long serialVersionUID = 5686539963576541197L;

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("sla-profile")
    private String slaProfile;
    @JsonProperty("circuit-priorities")
    private CircuitPriorities circuitPriorities;
    @JsonProperty("connection-selection-method")
    private String connectionSelectionMethod;
    @JsonProperty("sla-violation-action")
    private String slaViolationAction;
    @JsonProperty("evaluate-continuously")
    private String evaluateContinuously;
    @JsonProperty("recompute-timer")
    private String recomputeTimer;
    @JsonProperty("encryption")
    private String encryption;
    @JsonProperty("symmetric-forwarding")
    private String symmetricForwarding;
    @JsonProperty("load-balance")
    private String loadBalance;

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

    @JsonProperty("sla-profile")
    public String getSlaProfile() {
        return slaProfile;
    }

    @JsonProperty("sla-profile")
    public void setSlaProfile(String slaProfile) {
        this.slaProfile = slaProfile;
    }

    @JsonProperty("circuit-priorities")
    public CircuitPriorities getCircuitPriorities() {
        return circuitPriorities;
    }

    @JsonProperty("circuit-priorities")
    public void setCircuitPriorities(CircuitPriorities circuitPriorities) {
        this.circuitPriorities = circuitPriorities;
    }

    @JsonProperty("connection-selection-method")
    public String getConnectionSelectionMethod() {
        return connectionSelectionMethod;
    }

    @JsonProperty("connection-selection-method")
    public void setConnectionSelectionMethod(String connectionSelectionMethod) {
        this.connectionSelectionMethod = connectionSelectionMethod;
    }

    @JsonProperty("sla-violation-action")
    public String getSlaViolationAction() {
        return slaViolationAction;
    }

    @JsonProperty("sla-violation-action")
    public void setSlaViolationAction(String slaViolationAction) {
        this.slaViolationAction = slaViolationAction;
    }

    @JsonProperty("evaluate-continuously")
    public String getEvaluateContinuously() {
        return evaluateContinuously;
    }

    @JsonProperty("evaluate-continuously")
    public void setEvaluateContinuously(String evaluateContinuously) {
        this.evaluateContinuously = evaluateContinuously;
    }

    @JsonProperty("recompute-timer")
    public String getRecomputeTimer() {
        return recomputeTimer;
    }

    @JsonProperty("recompute-timer")
    public void setRecomputeTimer(String recomputeTimer) {
        this.recomputeTimer = recomputeTimer;
    }

    @JsonProperty("encryption")
    public String getEncryption() {
        return encryption;
    }

    @JsonProperty("encryption")
    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    @JsonProperty("symmetric-forwarding")
    public String getSymmetricForwarding() {
        return symmetricForwarding;
    }

    @JsonProperty("symmetric-forwarding")
    public void setSymmetricForwarding(String symmetricForwarding) {
        this.symmetricForwarding = symmetricForwarding;
    }

    @JsonProperty("load-balance")
    public String getLoadBalance() {
        return loadBalance;
    }

    @JsonProperty("load-balance")
    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
    }

}