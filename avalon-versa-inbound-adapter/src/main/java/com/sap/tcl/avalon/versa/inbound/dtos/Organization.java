package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "uuid", "name", "description", "subscription-plan", "id", "appliances", "parent-org" })
public class Organization implements Serializable {

    private static final long serialVersionUID = 7359327942033042011L;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("subscription-plan")
    private String subscriptionPlan;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("appliances")
    private List<String> appliances;
    @JsonProperty("parent-org")
    private String parentOrg;

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    @JsonProperty("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("subscription-plan")
    public String getSubscriptionPlan() {
        return subscriptionPlan;
    }

    @JsonProperty("subscription-plan")
    public void setSubscriptionPlan(String subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("appliances")
    public List<String> getAppliances() {
        return appliances;
    }

    @JsonProperty("appliances")
    public void setAppliances(List<String> appliances) {
        this.appliances = appliances;
    }

    @JsonProperty("parent-org")
    public String getParentOrg() {
        return parentOrg;
    }

    @JsonProperty("parent-org")
    public void setParentOrg(String parentOrg) {
        this.parentOrg = parentOrg;
    }

}
