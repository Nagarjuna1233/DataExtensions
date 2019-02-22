package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RulesPerPolicyResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("rule")
    private List<Rule> rule;

    @JsonProperty("rule")
    public List<Rule> getRule() {
        return rule;
    }

    @JsonProperty("rule")
    public void setRule(List<Rule> rule) {
        this.rule = rule;
    }
}