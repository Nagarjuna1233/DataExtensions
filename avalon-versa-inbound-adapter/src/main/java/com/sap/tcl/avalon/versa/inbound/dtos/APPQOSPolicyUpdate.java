package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class APPQOSPolicyUpdate implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("name")
    private String name;
    @JsonProperty("match")
    private APPQOSPolicyUpdateMatch match;
    @JsonProperty("set")
    private APPQOSPolicyUpdateSet set;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("match")
    public APPQOSPolicyUpdateMatch getMatch() {
        return match;
    }

    @JsonProperty("match")
    public void setMatch(APPQOSPolicyUpdateMatch match) {
        this.match = match;
    }

    @JsonProperty("set")
    public APPQOSPolicyUpdateSet getSet() {
        return set;
    }

    @JsonProperty("set")
    public void setSet(APPQOSPolicyUpdateSet set) {
        this.set = set;
    }

}
