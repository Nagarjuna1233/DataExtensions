package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteClassMap implements Serializable {

    private static final long serialVersionUID = -4493238370037505812L;

    @JsonProperty("name")
    private String name;

    @JsonProperty("protocol")
    private List<String> protocol;

    @JsonProperty("id")
    private String id;

    @JsonProperty("single-cpe-sites")
    private List<String> singleCpeSites;

    @JsonProperty("dual-cpe-site")
    private String dualCpeSite;

    @JsonProperty("single-cpe-dual-wan-site")
    private String singleCpeDualWanSite;

    @JsonProperty("single-cpe-site")
    private String singleCpeSite;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("protocol")
    public List<String> getProtocol() {
        return protocol;
    }

    @JsonProperty("protocol")
    public void setProtocol(List<String> protocol) {
        this.protocol = protocol;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("single-cpe-sites")
    public List<String> getSingleCpeSites() {
        return singleCpeSites;
    }

    @JsonProperty("single-cpe-sites")
    public void setSingleCpeSites(List<String> singleCpeSites) {
        this.singleCpeSites = singleCpeSites;
    }

    @JsonProperty("dual-cpe-site")
    public String getDualCpeSite() {
        return dualCpeSite;
    }

    @JsonProperty("dual-cpe-site")
    public void setDualCpeSite(String dualCpeSite) {
        this.dualCpeSite = dualCpeSite;
    }

    @JsonProperty("single-cpe-dual-wan-site")
    public String getSingleCpeDualWanSite() {
        return singleCpeDualWanSite;
    }

    @JsonProperty("single-cpe-dual-wan-site")
    public void setSingleCpeDualWanSite(String singleCpeDualWanSite) {
        this.singleCpeDualWanSite = singleCpeDualWanSite;
    }

    @JsonProperty("single-cpe-site")
    public String getSingleCpeSite() {
        return singleCpeSite;
    }

    @JsonProperty("single-cpe-site")
    public void setSingleCpeSite(String singleCpeSite) {
        this.singleCpeSite = singleCpeSite;
    }

}