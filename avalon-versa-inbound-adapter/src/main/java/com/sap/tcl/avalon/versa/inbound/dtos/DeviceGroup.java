package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "name", "organization", "enable-2factor-auth", "enable-staging-url", "staging-type",
        "staging-controller", "vpn-profile-name", "enable-one-time-password", "operations" })
public class DeviceGroup implements Serializable {

    private static final long serialVersionUID = -4601298269573852393L;
    @JsonProperty("name")
    private String name;
    @JsonProperty("organization")
    private String organization;
    @JsonProperty("enable-2factor-auth")
    private Boolean enable2factorAuth;
    @JsonProperty("enable-staging-url")
    private Boolean enableStagingUrl;
    @JsonProperty("staging-type")
    private String stagingType;
    @JsonProperty("staging-controller")
    private String stagingController;
    @JsonProperty("vpn-profile-name")
    private String vpnProfileName;
    @JsonProperty("enable-one-time-password")
    private Boolean enableOneTimePassword;
    @JsonProperty("operations")
    private Operations operations;
    @JsonProperty("serial-no")
    private String serialNo;

    @JsonProperty("inventory-name")
    private List<String> inventoryName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("e-mail")
    private String eMail;

    @JsonProperty("inventory-name")
    public List<String> getInventoryName() {
        return inventoryName;
    }

    @JsonProperty("inventory-name")
    public void setInventoryName(List<String> inventoryName) {
        this.inventoryName = inventoryName;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("e-mail")
    public String geteMail() {
        return eMail;
    }

    @JsonProperty("e-mail")
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    @JsonProperty("serial-no")
    public String getSerialNo() {
        return serialNo;
    }

    @JsonProperty("serial-no")
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("organization")
    public String getOrganization() {
        return organization;
    }

    @JsonProperty("organization")
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @JsonProperty("enable-2factor-auth")
    public Boolean getEnable2factorAuth() {
        return enable2factorAuth;
    }

    @JsonProperty("enable-2factor-auth")
    public void setEnable2factorAuth(Boolean enable2factorAuth) {
        this.enable2factorAuth = enable2factorAuth;
    }

    @JsonProperty("enable-staging-url")
    public Boolean getEnableStagingUrl() {
        return enableStagingUrl;
    }

    @JsonProperty("enable-staging-url")
    public void setEnableStagingUrl(Boolean enableStagingUrl) {
        this.enableStagingUrl = enableStagingUrl;
    }

    @JsonProperty("staging-type")
    public String getStagingType() {
        return stagingType;
    }

    @JsonProperty("staging-type")
    public void setStagingType(String stagingType) {
        this.stagingType = stagingType;
    }

    @JsonProperty("staging-controller")
    public String getStagingController() {
        return stagingController;
    }

    @JsonProperty("staging-controller")
    public void setStagingController(String stagingController) {
        this.stagingController = stagingController;
    }

    @JsonProperty("vpn-profile-name")
    public String getVpnProfileName() {
        return vpnProfileName;
    }

    @JsonProperty("vpn-profile-name")
    public void setVpnProfileName(String vpnProfileName) {
        this.vpnProfileName = vpnProfileName;
    }

    @JsonProperty("enable-one-time-password")
    public Boolean getEnableOneTimePassword() {
        return enableOneTimePassword;
    }

    @JsonProperty("enable-one-time-password")
    public void setEnableOneTimePassword(Boolean enableOneTimePassword) {
        this.enableOneTimePassword = enableOneTimePassword;
    }

    @JsonProperty("operations")
    public Operations getOperations() {
        return operations;
    }

    @JsonProperty("operations")
    public void setOperations(Operations operations) {
        this.operations = operations;
    }
}
