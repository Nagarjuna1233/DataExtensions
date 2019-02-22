package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "protocol", "dps", "name", "description", "match-type" })
public class CpedeploymentClassMap implements Serializable {

    private static final long serialVersionUID = 4622019645869702572L;
    @JsonProperty("protocol")
    private List<String> protocol;
    @JsonProperty("dps")
    private boolean dps;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("match-type")
    private String matchType;

    @JsonProperty("protocol")
    public List<String> getProtocol() {
        return protocol;
    }

    @JsonProperty("protocol")
    public void setProtocol(List<String> protocol) {
        this.protocol = protocol;
    }

    @JsonProperty("dps")
    public Boolean getDps() {
        return dps;
    }

    @JsonProperty("dps")
    public void setDps(Boolean dps) {
        this.dps = dps;
    }

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

    @JsonProperty("match-type")
    public String getMatchType() {
        return matchType;
    }

    @JsonProperty("match-type")
    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

}