package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("single-cpe-dual-wan-site-services")
    private List<SiteServices> singleCpeDualWanSiteServices;

    @JsonProperty("single-cpe-site-services")
    private List<SiteServices> singleCpeSiteServices;

    @JsonProperty("dual-cpe-site-services")
    private List<SiteServices> dualCpeSiteServices;

    @JsonProperty("single-cpe-dual-wan-site-services")
    public List<SiteServices> getSingleCpeDualWanSiteServices() {
        return singleCpeDualWanSiteServices;
    }

    @JsonProperty("single-cpe-dual-wan-site-services")
    public void setSingleCpeDualWanSiteServices(List<SiteServices> singleCpeDualWanSiteServices) {
        this.singleCpeDualWanSiteServices = singleCpeDualWanSiteServices;
    }

    @JsonProperty("single-cpe-site-services")
    public List<SiteServices> getSingleCpeSiteServices() {
        return singleCpeSiteServices;
    }

    @JsonProperty("single-cpe-site-services")
    public void setSingleCpeSiteServices(List<SiteServices> singleCpeSiteServices) {
        this.singleCpeSiteServices = singleCpeSiteServices;
    }

    @JsonProperty("dual-cpe-site-services")
    public List<SiteServices> getDualCpeSiteServices() {
        return dualCpeSiteServices;
    }

    @JsonProperty("dual-cpe-site-services")
    public void setDualCpeSiteServices(List<SiteServices> dualCpeSiteServices) {
        this.dualCpeSiteServices = dualCpeSiteServices;
    }

}
