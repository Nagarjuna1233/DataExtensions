package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class APPQOSRulesApplication implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @JsonProperty("predefined-application-list")
    private List<String> predefinedApplicationList;
    @JsonProperty("user-defined-application-list")
    private List<String> userDefinedApplicationList;

    @JsonProperty("predefined-application-list")
    public List<String> getPredefinedApplicationList() {
        return predefinedApplicationList;
    }

    @JsonProperty("predefined-application-list")
    public void setPredefinedApplicationList(List<String> predefinedApplicationList) {
        this.predefinedApplicationList = predefinedApplicationList;
    }

    @JsonProperty("user-defined-application-list")
    public List<String> getUserDefinedApplicationList() {
        return userDefinedApplicationList;
    }

    @JsonProperty("user-defined-application-list")
    public void setUserDefinedApplicationList(List<String> userDefinedApplicationList) {
        this.userDefinedApplicationList = userDefinedApplicationList;
    }

}
