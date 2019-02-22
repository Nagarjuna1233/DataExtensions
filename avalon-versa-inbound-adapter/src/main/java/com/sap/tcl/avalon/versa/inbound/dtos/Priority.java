package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "value", "description", "circuit-names", "circuit-types", "circuit-media" })
public class Priority implements Serializable {

    private static final long serialVersionUID = 7221617731107576140L;

    @JsonProperty("value")
    private String value;
    @JsonProperty("description")
    private String description;
    @JsonProperty("circuit-names")
    private CircuitNames circuitNames;
    @JsonProperty("circuit-types")
    private CircuitTypes circuitTypes;
    @JsonProperty("circuit-media")
    private CircuitMedia circuitMedia;

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("circuit-names")
    public CircuitNames getCircuitNames() {
        return circuitNames;
    }

    @JsonProperty("circuit-names")
    public void setCircuitNames(CircuitNames circuitNames) {
        this.circuitNames = circuitNames;
    }

    @JsonProperty("circuit-types")
    public CircuitTypes getCircuitTypes() {
        return circuitTypes;
    }

    @JsonProperty("circuit-types")
    public void setCircuitTypes(CircuitTypes circuitTypes) {
        this.circuitTypes = circuitTypes;
    }

    @JsonProperty("circuit-media")
    public CircuitMedia getCircuitMedia() {
        return circuitMedia;
    }

    @JsonProperty("circuit-media")
    public void setCircuitMedia(CircuitMedia circuitMedia) {
        this.circuitMedia = circuitMedia;
    }

}