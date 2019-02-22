package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "rule", "statistics" })
public class Rules implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("rule")
    private List<Rule> rule;
    @JsonProperty("statistics")
    private Statistics statistics;

    @JsonProperty("rule")
    public List<Rule> getRule() {
        return rule;
    }

    @JsonProperty("rule")
    public void setRule(List<Rule> rule) {
        this.rule = rule;
    }

    @JsonProperty("statistics")
    public Statistics getStatistics() {
        return statistics;
    }

    @JsonProperty("statistics")
    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

}