package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Source implements Serializable {

    private static final long serialVersionUID = 5072386419032063364L;
    @JsonProperty("region")
    private List<String> region;
    @JsonProperty("zone")
    private Zone zone;
    @JsonProperty("user")
    private User user;

    @JsonProperty("region")
    public List<String> getRegion() {
        return region;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("region")
    public void setRegion(List<String> region) {
        this.region = region;
    }

    @JsonProperty("zone")
    public Zone getZone() {
        return zone;
    }

    @JsonProperty("zone")
    public void setZone(Zone zone) {
        this.zone = zone;
    }

}
