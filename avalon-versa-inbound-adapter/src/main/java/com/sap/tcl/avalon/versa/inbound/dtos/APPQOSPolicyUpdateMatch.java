package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APPQOSPolicyUpdateMatch implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("url-category")
    private APPQOSPolicyUpdateUrlCategory urlCategory;
    @JsonProperty("application")
    private APPQOSPolicyUpdateApplication application;

    @JsonProperty("url-category")
    public APPQOSPolicyUpdateUrlCategory getUrlCategory() {
        return urlCategory;
    }

    @JsonProperty("url-category")
    public void setUrlCategory(APPQOSPolicyUpdateUrlCategory urlCategory) {
        this.urlCategory = urlCategory;
    }

    @JsonProperty("application")
    public APPQOSPolicyUpdateApplication getApplication() {
        return application;
    }

    @JsonProperty("application")
    public void setApplication(APPQOSPolicyUpdateApplication application) {
        this.application = application;
    }

}
