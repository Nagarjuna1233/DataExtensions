
package com.sap.tcl.avalon.anuta.inbound.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "cpedeployment:class-map" })
public class PredefinedApplications {

    @JsonProperty("cpedeployment:class-map")
    private CpedeploymentClassMap cpedeploymentClassMap;

    @JsonProperty("cpedeployment:class-map")
    public CpedeploymentClassMap getCpedeploymentClassMap() {
        return cpedeploymentClassMap;
    }

    @JsonProperty("cpedeployment:class-map")
    public void setCpedeploymentClassMap(CpedeploymentClassMap cpedeploymentClassMap) {
        this.cpedeploymentClassMap = cpedeploymentClassMap;
    }
}