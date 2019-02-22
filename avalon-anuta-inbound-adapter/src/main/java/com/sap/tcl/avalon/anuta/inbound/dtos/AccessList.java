package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "cascade", "access-list-rules", "name" })
public class AccessList implements Serializable {

    private static final long serialVersionUID = -2178097102560089601L;

    @JsonProperty("cascade")
    private Cascade cascade;
    @JsonProperty("access-list-rules")
    private List<AccessListRule> accessListRules;
    @JsonProperty("name")
    private String name;

    @JsonProperty("@")
    private DeviceOwner deviceOwner;

    @JsonProperty("@")
    public DeviceOwner getDeviceOwner() {
        return deviceOwner;
    }

    @JsonProperty("@")
    public void setDeviceOwner(DeviceOwner deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

    @JsonProperty("cascade")
    public Cascade getCascade() {
        return cascade;
    }

    @JsonProperty("cascade")
    public void setCascade(Cascade cascade) {
        this.cascade = cascade;
    }

    @JsonProperty("access-list-rules")
    public List<AccessListRule> getAccessListRules() {
        return accessListRules;
    }

    @JsonProperty("access-list-rules")
    public void setAccessListRules(List<AccessListRule> accessListRules) {
        this.accessListRules = accessListRules;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

}