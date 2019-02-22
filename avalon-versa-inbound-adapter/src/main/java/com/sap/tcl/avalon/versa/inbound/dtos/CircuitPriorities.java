package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "priority", "avoid" })
public class CircuitPriorities implements Serializable {

    private static final long serialVersionUID = 8194776564338300916L;

    @JsonProperty("priority")
    private List<Priority> priority;
    @JsonProperty("avoid")
    private Avoid avoid;

    @JsonProperty("priority")
    public List<Priority> getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(List<Priority> priority) {
        this.priority = priority;
    }

    @JsonProperty("avoid")
    public Avoid getAvoid() {
        return avoid;
    }

    @JsonProperty("avoid")
    public void setAvoid(Avoid avoid) {
        this.avoid = avoid;
    }

}