package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomApp implements Serializable {

    private static final long serialVersionUID = 325305513555626341L;
    @JsonProperty("update-class-map")
    private UpdateClassMap updateClassMap;

    @JsonProperty("update-class-map")
    public UpdateClassMap getUpdateClassMap() {
        return updateClassMap;
    }

    @JsonProperty("update-class-map")
    public void setUpdateClassMap(UpdateClassMap updateClassMap) {
        this.updateClassMap = updateClassMap;
    }

}