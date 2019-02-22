package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "Owner", "protocol", "description", "match-type", "name", "Shared With", "access-group" })
public class AppsCpedeploymentClassMap implements Serializable {

    private static final long serialVersionUID = -8807886321597451793L;

    @JsonProperty("Owner")
    private List<String> owner;
    @JsonProperty("protocol")
    private List<String> protocol;
    @JsonProperty("description")
    private String description;
    @JsonProperty("match-type")
    private String matchType;
    @JsonProperty("name")
    private String name;
    @JsonProperty("Shared With")
    private List<List<String>> sharedWith;
    @JsonProperty("access-group")
    private List<String> accessGroup;

    @JsonProperty("Owner")
    public List<String> getOwner() {
        return owner;
    }

    @JsonProperty("Owner")
    public void setOwner(List<String> owner) {
        this.owner = owner;
    }

    @JsonProperty("protocol")
    public List<String> getProtocol() {
        return protocol;
    }

    @JsonProperty("protocol")
    public void setProtocol(List<String> protocol) {
        this.protocol = protocol;
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

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Shared With")
    public List<List<String>> getSharedWith() {
        return sharedWith;
    }

    @JsonProperty("Shared With")
    public void setSharedWith(List<List<String>> sharedWith) {
        this.sharedWith = sharedWith;
    }

    @JsonProperty("access-group")
    public List<String> getAccessGroup() {
        return accessGroup;
    }

    @JsonProperty("access-group")
    public void setAccessGroup(List<String> accessGroup) {
        this.accessGroup = accessGroup;
    }
}