package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllSitesResponse implements Serializable {

    private static final long serialVersionUID = -2984833387849006954L;

    @JsonProperty("cpedeployment:managed-cpe-services")
    private CpedeploymentManagedCpeServices cpedeploymentManagedCpeServices;

    @JsonProperty("cpedeployment:managed-cpe-services")
    public CpedeploymentManagedCpeServices getCpedeploymentManagedCpeServices() {
        return cpedeploymentManagedCpeServices;
    }

    @JsonProperty("cpedeployment:managed-cpe-services")
    public void setCpedeploymentManagedCpeServices(CpedeploymentManagedCpeServices cpedeploymentManagedCpeServices) {
        this.cpedeploymentManagedCpeServices = cpedeploymentManagedCpeServices;
    }
}
