package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "service-provisioning-status", "id", "interface:interfaces", "os-version", "device-type", "@",
        "status", "serial-number", "l3features:routes" })
public class Device implements Serializable {

    private static final long serialVersionUID = -5666603830267182360L;
    @JsonProperty("service-provisioning-status")
    private String serviceProvisioningStatus;
    @JsonProperty("id")
    private String id;
    @JsonProperty("interface:interfaces")
    private InterfaceInterfaces interfaceInterfaces;
    @JsonProperty("os-version")
    private String osVersion;
    @JsonProperty("device-type")
    private String deviceType;
    @JsonProperty("@")
    private DeviceOwner deviceOwner;
    @JsonProperty("status")
    private String status;
    @JsonProperty("serial-number")
    private String serialNumber;
    @JsonProperty("l3features:routes")
    private L3featuresRoutes l3featuresRoutes;

    @JsonProperty("service-provisioning-status")
    public String getServiceProvisioningStatus() {
        return serviceProvisioningStatus;
    }

    @JsonProperty("service-provisioning-status")
    public void setServiceProvisioningStatus(String serviceProvisioningStatus) {
        this.serviceProvisioningStatus = serviceProvisioningStatus;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("interface:interfaces")
    public InterfaceInterfaces getInterfaceInterfaces() {
        return interfaceInterfaces;
    }

    @JsonProperty("interface:interfaces")
    public void setInterfaceInterfaces(InterfaceInterfaces interfaceInterfaces) {
        this.interfaceInterfaces = interfaceInterfaces;
    }

    @JsonProperty("os-version")
    public String getOsVersion() {
        return osVersion;
    }

    @JsonProperty("os-version")
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    @JsonProperty("device-type")
    public String getDeviceType() {
        return deviceType;
    }

    @JsonProperty("device-type")
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @JsonProperty("@")
    public DeviceOwner getDeviceOwner() {
        return deviceOwner;
    }

    @JsonProperty("@")
    public void setDeviceOwner(DeviceOwner deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("serial-number")
    public String getSerialNumber() {
        return serialNumber;
    }

    @JsonProperty("serial-number")
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @JsonProperty("l3features:routes")
    public L3featuresRoutes getL3featuresRoutes() {
        return l3featuresRoutes;
    }

    @JsonProperty("l3features:routes")
    public void setL3featuresRoutes(L3featuresRoutes l3featuresRoutes) {
        this.l3featuresRoutes = l3featuresRoutes;
    }
}