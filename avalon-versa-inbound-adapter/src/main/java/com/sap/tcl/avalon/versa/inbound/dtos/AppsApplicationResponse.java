package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "application" })
public class AppsApplicationResponse implements Serializable {

    private static final long serialVersionUID = 228919589437842857L;

    @JsonProperty("application")
    private List<AppsApplication> application;

    @JsonProperty("application")
    public List<AppsApplication> getApplication() {
        return application;
    }

    @JsonProperty("application")
    public void setApplication(List<AppsApplication> application) {
        this.application = application;
    }
}