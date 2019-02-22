package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class APPQOSPolicy implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("name")
    private String name;
    @JsonProperty("match")
    private APPQOSRulesMatch match;
    @JsonProperty("set")
    private APPQOSRulesSet set;

    @JsonProperty("description")
    private String description;

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("match")
    public APPQOSRulesMatch getMatch() {
        return match;
    }

    @JsonProperty("match")
    public void setMatch(APPQOSRulesMatch match) {
        this.match = match;
    }

    @JsonProperty("set")
    public APPQOSRulesSet getSet() {
        return set;
    }

    @JsonProperty("set")
    public void setSet(APPQOSRulesSet set) {
        this.set = set;
    }
}
