package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "customer" })
public class CpedeploymentManagedCpeServices implements Serializable {

    private static final long serialVersionUID = -7582813533123128571L;
    @JsonProperty("customer")
    private List<Customer> customer;

    @JsonProperty("customer")
    public List<Customer> getCustomer() {
        return customer;
    }

    @JsonProperty("customer")
    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }
}