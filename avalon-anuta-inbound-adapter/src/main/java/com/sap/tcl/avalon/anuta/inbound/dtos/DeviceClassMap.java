package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "cascade-sharedwith", "cascade-owner" })
public class DeviceClassMap implements Serializable {

    private static final long serialVersionUID = -4233852816178752302L;
    @JsonProperty("cascade-sharedwith")
    private String cascadeSharedwith;
    @JsonProperty("cascade-owner")
    private String cascadeOwner;

    @JsonProperty("cascade-sharedwith")
    public String getCascadeSharedwith() {
        return cascadeSharedwith;
    }

    @JsonProperty("cascade-sharedwith")
    public void setCascadeSharedwith(String cascadeSharedwith) {
        this.cascadeSharedwith = cascadeSharedwith;
    }

    @JsonProperty("cascade-owner")
    public String getCascadeOwner() {
        return cascadeOwner;
    }

    @JsonProperty("cascade-owner")
    public void setCascadeOwner(String cascadeOwner) {
        this.cascadeOwner = cascadeOwner;
    }
}