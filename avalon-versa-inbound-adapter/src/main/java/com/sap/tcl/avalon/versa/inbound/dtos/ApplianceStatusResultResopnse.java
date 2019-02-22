package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "versanms.ApplianceStatusResult" })
public class ApplianceStatusResultResopnse implements Serializable {

    private static final long serialVersionUID = -8192473178737066371L;
    @JsonProperty("versanms.ApplianceStatusResult")
    private VersanmsApplianceStatusResultResponse versanmsApplianceStatusResult;

    @JsonProperty("versanms.ApplianceStatusResult")
    public VersanmsApplianceStatusResultResponse getVersanmsApplianceStatusResult() {
        return versanmsApplianceStatusResult;
    }

    @JsonProperty("versanms.ApplianceStatusResult")
    public void setVersanmsApplianceStatusResult(VersanmsApplianceStatusResultResponse versanmsApplianceStatusResult) {
        this.versanmsApplianceStatusResult = versanmsApplianceStatusResult;
    }
}
