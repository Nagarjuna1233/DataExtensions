package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "cascade", "access-list" })
public class AccessLists implements Serializable {

    private static final long serialVersionUID = -454093047397582061L;
    @JsonProperty("cascade")
    private Cascade cascade;
    @JsonProperty("access-list")
    private List<AccessList> accessList;

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

    @JsonProperty("access-list")
    public List<AccessList> getAccessList() {
        return accessList;
    }

    @JsonProperty("access-list")
    public void setAccessList(List<AccessList> accessList) {
        this.accessList = accessList;
    }

}