package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "cascade-owner", "cascade-sharedwith" })
public class CascadeDeviceOwner implements Serializable {

    private static final long serialVersionUID = -427178096933905382L;
    @JsonProperty("cascade-sharedwith")
    private boolean cascadeSharedwith;
    @JsonProperty("cascade-owner")
    private boolean cascadeOwner;

    @JsonProperty("cascade-sharedwith")
    public boolean isCascadeSharedwith() {
        return cascadeSharedwith;
    }

    @JsonProperty("cascade-sharedwith")
    public void setCascadeSharedwith(boolean cascadeSharedwith) {
        this.cascadeSharedwith = cascadeSharedwith;
    }

    @JsonProperty("cascade-owner")
    public boolean isCascadeOwner() {
        return cascadeOwner;
    }

    @JsonProperty("cascade-owner")
    public void setCascadeOwner(boolean cascadeOwner) {
        this.cascadeOwner = cascadeOwner;
    }
}
