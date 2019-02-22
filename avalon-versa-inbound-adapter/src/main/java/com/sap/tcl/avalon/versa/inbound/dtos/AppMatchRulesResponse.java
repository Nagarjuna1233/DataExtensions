package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "app-match-rules" })
public class AppMatchRulesResponse implements Serializable {

    private static final long serialVersionUID = -7988141173055838051L;

    @JsonProperty("app-match-rules")
    private List<AppMatchRule> appMatchRules;

    @JsonProperty("app-match-rules")
    public List<AppMatchRule> getAppMatchRules() {
        return appMatchRules;
    }

    @JsonProperty("app-match-rules")
    public void setAppMatchRules(List<AppMatchRule> appMatchRules) {
        this.appMatchRules = appMatchRules;
    }

}