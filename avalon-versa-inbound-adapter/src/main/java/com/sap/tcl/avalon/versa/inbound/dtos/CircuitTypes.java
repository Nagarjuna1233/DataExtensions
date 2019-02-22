package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "local", "remote" })
public class CircuitTypes implements Serializable {

    private static final long serialVersionUID = -1073241288142567301L;

    @JsonProperty("local")
    private List<String> local;
    @JsonProperty("remote")
    private List<String> remote;

    @JsonProperty("local")
    public List<String> getLocal() {
        return local;
    }

    @JsonProperty("local")
    public void setLocal(List<String> local) {
        this.local = local;
    }

    @JsonProperty("remote")
    public List<String> getRemote() {
        return remote;
    }

    @JsonProperty("remote")
    public void setRemote(List<String> remote) {
        this.remote = remote;
    }

}