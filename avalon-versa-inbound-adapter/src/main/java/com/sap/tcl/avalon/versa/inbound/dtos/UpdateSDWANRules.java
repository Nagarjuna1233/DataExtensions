package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "rule" })
public class UpdateSDWANRules implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("rule")
    private Rule rule;

    @JsonProperty("rule")
    public Rule getRule() {
        return rule;
    }

    @JsonProperty("rule")
    public void setRule(Rule rule) {
        this.rule = rule;
    }

}