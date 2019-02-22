package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "organization" })
public class OrganisationResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("organization")
    private List<Organization> organization;

    @JsonProperty("organization")
    public List<Organization> getOrganization() {
        return organization;
    }

    @JsonProperty("organization")
    public void setOrganization(List<Organization> organization) {
        this.organization = organization;
    }

}