package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class APPQOSRulesSource implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("user")
    private APPQOSRulesUser user;

    @JsonProperty("user")
    public APPQOSRulesUser getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(APPQOSRulesUser user) {
        this.user = user;
    }

}
