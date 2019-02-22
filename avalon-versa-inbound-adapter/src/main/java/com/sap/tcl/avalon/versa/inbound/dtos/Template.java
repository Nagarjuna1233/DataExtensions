package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "name", "templateType", "providerTenant", "organization", "device-group", "solutionTier",
        "bandwidth", "isAnalyticsEnabled", "isPrimary" })
public class Template implements Serializable {

    private static final long serialVersionUID = 2465198965777841686L;

    @JsonProperty("name")
    private String name;
    @JsonProperty("templateType")
    private String templateType;
    @JsonProperty("providerTenant")
    private String providerTenant;
    @JsonProperty("organization")
    private String organization;
    @JsonProperty("device-group")
    private String deviceGroup;
    @JsonProperty("solutionTier")
    private String solutionTier;
    @JsonProperty("bandwidth")
    private Integer bandwidth;
    @JsonProperty("isAnalyticsEnabled")
    private Boolean isAnalyticsEnabled;
    @JsonProperty("isPrimary")
    private Boolean isPrimary;
    @JsonProperty("compositeOrPrimary")
    private String compositeOrPrimary;

    @JsonProperty("versionNo")
    private String versionNo;

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    @JsonProperty("compositeOrPrimary")
    public String getCompositeOrPrimary() {
        return compositeOrPrimary;
    }

    @JsonProperty("compositeOrPrimary")
    public void setCompositeOrPrimary(String compositeOrPrimary) {
        this.compositeOrPrimary = compositeOrPrimary;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("templateType")
    public String getTemplateType() {
        return templateType;
    }

    @JsonProperty("templateType")
    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    @JsonProperty("providerTenant")
    public String getProviderTenant() {
        return providerTenant;
    }

    @JsonProperty("providerTenant")
    public void setProviderTenant(String providerTenant) {
        this.providerTenant = providerTenant;
    }

    @JsonProperty("organization")
    public String getOrganization() {
        return organization;
    }

    @JsonProperty("organization")
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @JsonProperty("device-group")
    public String getDeviceGroup() {
        return deviceGroup;
    }

    @JsonProperty("device-group")
    public void setDeviceGroup(String deviceGroup) {
        this.deviceGroup = deviceGroup;
    }

    @JsonProperty("solutionTier")
    public String getSolutionTier() {
        return solutionTier;
    }

    @JsonProperty("solutionTier")
    public void setSolutionTier(String solutionTier) {
        this.solutionTier = solutionTier;
    }

    @JsonProperty("bandwidth")
    public Integer getBandwidth() {
        return bandwidth;
    }

    @JsonProperty("bandwidth")
    public void setBandwidth(Integer bandwidth) {
        this.bandwidth = bandwidth;
    }

    @JsonProperty("isAnalyticsEnabled")
    public Boolean getIsAnalyticsEnabled() {
        return isAnalyticsEnabled;
    }

    @JsonProperty("isAnalyticsEnabled")
    public void setIsAnalyticsEnabled(Boolean isAnalyticsEnabled) {
        this.isAnalyticsEnabled = isAnalyticsEnabled;
    }

    @JsonProperty("isPrimary")
    public Boolean getIsPrimary() {
        return isPrimary;
    }

    @JsonProperty("isPrimary")
    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    @Override
    public String toString() {
        return "templateType:" + templateType + ",organization" + organization;

    }

}