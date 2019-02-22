package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "user-defined-application" })
public class UserDefinedApplicationResponse implements Serializable {

    private static final long serialVersionUID = -5765388796785543861L;

    @JsonProperty("user-defined-application")
    private List<UserDefinedApplication> userDefinedApplication;

    @JsonProperty("user-defined-application")
    public List<UserDefinedApplication> getUserDefinedApplication() {
        return userDefinedApplication;
    }

    @JsonProperty("user-defined-application")
    public void setUserDefinedApplication(List<UserDefinedApplication> userDefinedApplication) {
        this.userDefinedApplication = userDefinedApplication;
    }
}