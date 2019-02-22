package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "name", "access-lists" })
public class Customer implements Serializable {

    private static final long serialVersionUID = 7417720775892278160L;
    @JsonProperty("name")
    private String name;
    @JsonProperty("access-lists")
    private AccessLists accessLists;

    @JsonProperty("single-cpe-dual-wan-site")
    private Site singleCpeDualWanSite;

    @JsonProperty("single-cpe-site")
    private Site singleCpeSite;

    @JsonProperty("dual-cpe-site")
    private Site dualCpeSite;

    @JsonProperty("single-cpe-dual-wan-site")
    public Site getSingleCpeDualWanSite() {
        return singleCpeDualWanSite;
    }

    @JsonProperty("single-cpe-dual-wan-site")
    public void setSingleCpeDualWanSite(Site singleCpeDualWanSite) {
        this.singleCpeDualWanSite = singleCpeDualWanSite;
    }

    @JsonProperty("single-cpe-site")
    public Site getSingleCpeSite() {
        return singleCpeSite;
    }

    @JsonProperty("single-cpe-site")
    public void setSingleCpeSite(Site singleCpeSite) {
        this.singleCpeSite = singleCpeSite;
    }

    @JsonProperty("dual-cpe-site")
    public Site getDualCpeSite() {
        return dualCpeSite;
    }

    @JsonProperty("dual-cpe-site")
    public void setDualCpeSite(Site dualCpeSite) {
        this.dualCpeSite = dualCpeSite;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("access-lists")
    public AccessLists getAccessLists() {
        return accessLists;
    }

    @JsonProperty("access-lists")
    public void setAccessLists(AccessLists accessLists) {
        this.accessLists = accessLists;
    }
}