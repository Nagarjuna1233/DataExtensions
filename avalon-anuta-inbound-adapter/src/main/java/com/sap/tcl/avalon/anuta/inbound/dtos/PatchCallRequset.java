package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatchCallRequset implements Serializable {

    private static final long serialVersionUID = -7429252569140856160L;
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