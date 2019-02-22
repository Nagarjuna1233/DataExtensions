package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "cascade-owner" })
public class Cascade implements Serializable {

    private static final long serialVersionUID = -7564139147886946189L;
    @JsonProperty("cascade-owner")
    private String cascadeOwner;

    @JsonProperty("cascade-owner")
    public String getCascadeOwner() {
        return cascadeOwner;
    }

    @JsonProperty("cascade-owner")
    public void setCascadeOwner(String cascadeOwner) {
        this.cascadeOwner = cascadeOwner;
    }
}