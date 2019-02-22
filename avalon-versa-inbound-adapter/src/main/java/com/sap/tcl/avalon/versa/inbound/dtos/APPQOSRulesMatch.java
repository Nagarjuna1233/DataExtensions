package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class APPQOSRulesMatch implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("source")
    private APPQOSRulesSource source;
    @JsonProperty("destination")
    private APPQOSRulesDestination destination;
    @JsonProperty("application")
    private APPQOSRulesApplication application;
    @JsonProperty("url-category")
    private APPQOSRulesUrlCategory urlCategory;

    @JsonProperty("source")
    public APPQOSRulesSource getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(APPQOSRulesSource source) {
        this.source = source;
    }

    @JsonProperty("destination")
    public APPQOSRulesDestination getDestination() {
        return destination;
    }

    @JsonProperty("destination")
    public void setDestination(APPQOSRulesDestination destination) {
        this.destination = destination;
    }

    @JsonProperty("application")
    public APPQOSRulesApplication getApplication() {
        return application;
    }

    @JsonProperty("application")
    public void setApplication(APPQOSRulesApplication application) {
        this.application = application;
    }

    @JsonProperty("url-category")
    public APPQOSRulesUrlCategory getUrlCategory() {
        return urlCategory;
    }

    @JsonProperty("url-category")
    public void setUrlCategory(APPQOSRulesUrlCategory urlCategory) {
        this.urlCategory = urlCategory;
    }

}
