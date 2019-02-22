package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDeletion implements Serializable {

    private static final long serialVersionUID = 6113531918245327396L;
    @JsonProperty("delete-class-map")
    private DeleteClassMap deleteClassMap;

    @JsonProperty("delete-class-map")
    public DeleteClassMap getDeleteClassMap() {
        return deleteClassMap;
    }

    @JsonProperty("delete-class-map")
    public void setDeleteClassMap(DeleteClassMap deleteClassMap) {
        this.deleteClassMap = deleteClassMap;
    }

}