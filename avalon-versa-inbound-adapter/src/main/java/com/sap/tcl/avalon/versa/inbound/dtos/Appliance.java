package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "name", "uuid", "last-updated-time", "ping-status", "sync-status", "createdAt",
        "yang-compatibility-status", "services-status", "overall-status", "controller-status", "path-status",
        "intra-chassis-ha-status", "inter-chassis-ha-status", "ownerOrg", "type", "cmsOrg", "orgs", "sngCount",
        "softwareVersion", "currentUpgradeStatus", "lastUpgradeStatus", "connector", "branchId", "services",
        "ipAddress", "location", "startTime", "refreshCycleCount", "subType", "branch-maintenance-mode",
        "controllers" })
public class Appliance implements Serializable {

    private static final long serialVersionUID = -3366415449206768946L;
    @JsonProperty("name")
    private String name;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("last-updated-time")
    private String lastUpdatedTime;
    @JsonProperty("ping-status")
    private String pingStatus;
    @JsonProperty("sync-status")
    private String syncStatus;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("yang-compatibility-status")
    private String yangCompatibilityStatus;
    @JsonProperty("services-status")
    private String servicesStatus;
    @JsonProperty("overall-status")
    private String overallStatus;
    @JsonProperty("controller-status")
    private String controllerStatus;
    @JsonProperty("path-status")
    private String pathStatus;
    @JsonProperty("intra-chassis-ha-status")
    private IntraChassisHaStatus intraChassisHaStatus;
    @JsonProperty("inter-chassis-ha-status")
    private InterChassisHaStatus interChassisHaStatus;
    @JsonProperty("ownerOrg")
    private String ownerOrg;
    @JsonProperty("type")
    private String type;
    @JsonProperty("cmsOrg")
    private String cmsOrg;
    @JsonProperty("orgs")
    private List<String> orgs;
    @JsonProperty("sngCount")
    private Integer sngCount;
    @JsonProperty("softwareVersion")
    private String softwareVersion;
    @JsonProperty("currentUpgradeStatus")
    private String currentUpgradeStatus;
    @JsonProperty("lastUpgradeStatus")
    private String lastUpgradeStatus;
    @JsonProperty("connector")
    private String connector;
    @JsonProperty("branchId")
    private Integer branchId;
    @JsonProperty("services")
    private List<String> services;
    @JsonProperty("ipAddress")
    private String ipAddress;
    @JsonProperty("location")
    private String location;
    @JsonProperty("startTime")
    private String startTime;
    @JsonProperty("refreshCycleCount")
    private Integer refreshCycleCount;
    @JsonProperty("subType")
    private String subType;
    @JsonProperty("branch-maintenance-mode")
    private Boolean branchMaintenanceMode;
    @JsonProperty("controllers")
    private List<String> controllers;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    @JsonProperty("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("last-updated-time")
    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    @JsonProperty("last-updated-time")
    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    @JsonProperty("ping-status")
    public String getPingStatus() {
        return pingStatus;
    }

    @JsonProperty("ping-status")
    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
    }

    @JsonProperty("sync-status")
    public String getSyncStatus() {
        return syncStatus;
    }

    @JsonProperty("sync-status")
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("yang-compatibility-status")
    public String getYangCompatibilityStatus() {
        return yangCompatibilityStatus;
    }

    @JsonProperty("yang-compatibility-status")
    public void setYangCompatibilityStatus(String yangCompatibilityStatus) {
        this.yangCompatibilityStatus = yangCompatibilityStatus;
    }

    @JsonProperty("services-status")
    public String getServicesStatus() {
        return servicesStatus;
    }

    @JsonProperty("services-status")
    public void setServicesStatus(String servicesStatus) {
        this.servicesStatus = servicesStatus;
    }

    @JsonProperty("overall-status")
    public String getOverallStatus() {
        return overallStatus;
    }

    @JsonProperty("overall-status")
    public void setOverallStatus(String overallStatus) {
        this.overallStatus = overallStatus;
    }

    @JsonProperty("controller-status")
    public String getControllerStatus() {
        return controllerStatus;
    }

    @JsonProperty("controller-status")
    public void setControllerStatus(String controllerStatus) {
        this.controllerStatus = controllerStatus;
    }

    @JsonProperty("path-status")
    public String getPathStatus() {
        return pathStatus;
    }

    @JsonProperty("path-status")
    public void setPathStatus(String pathStatus) {
        this.pathStatus = pathStatus;
    }

    @JsonProperty("intra-chassis-ha-status")
    public IntraChassisHaStatus getIntraChassisHaStatus() {
        return intraChassisHaStatus;
    }

    @JsonProperty("intra-chassis-ha-status")
    public void setIntraChassisHaStatus(IntraChassisHaStatus intraChassisHaStatus) {
        this.intraChassisHaStatus = intraChassisHaStatus;
    }

    @JsonProperty("inter-chassis-ha-status")
    public InterChassisHaStatus getInterChassisHaStatus() {
        return interChassisHaStatus;
    }

    @JsonProperty("inter-chassis-ha-status")
    public void setInterChassisHaStatus(InterChassisHaStatus interChassisHaStatus) {
        this.interChassisHaStatus = interChassisHaStatus;
    }

    @JsonProperty("ownerOrg")
    public String getOwnerOrg() {
        return ownerOrg;
    }

    @JsonProperty("ownerOrg")
    public void setOwnerOrg(String ownerOrg) {
        this.ownerOrg = ownerOrg;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("cmsOrg")
    public String getCmsOrg() {
        return cmsOrg;
    }

    @JsonProperty("cmsOrg")
    public void setCmsOrg(String cmsOrg) {
        this.cmsOrg = cmsOrg;
    }

    @JsonProperty("orgs")
    public List<String> getOrgs() {
        return orgs;
    }

    @JsonProperty("orgs")
    public void setOrgs(List<String> orgs) {
        this.orgs = orgs;
    }

    @JsonProperty("sngCount")
    public Integer getSngCount() {
        return sngCount;
    }

    @JsonProperty("sngCount")
    public void setSngCount(Integer sngCount) {
        this.sngCount = sngCount;
    }

    @JsonProperty("softwareVersion")
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    @JsonProperty("softwareVersion")
    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    @JsonProperty("currentUpgradeStatus")
    public String getCurrentUpgradeStatus() {
        return currentUpgradeStatus;
    }

    @JsonProperty("currentUpgradeStatus")
    public void setCurrentUpgradeStatus(String currentUpgradeStatus) {
        this.currentUpgradeStatus = currentUpgradeStatus;
    }

    @JsonProperty("lastUpgradeStatus")
    public String getLastUpgradeStatus() {
        return lastUpgradeStatus;
    }

    @JsonProperty("lastUpgradeStatus")
    public void setLastUpgradeStatus(String lastUpgradeStatus) {
        this.lastUpgradeStatus = lastUpgradeStatus;
    }

    @JsonProperty("connector")
    public String getConnector() {
        return connector;
    }

    @JsonProperty("connector")
    public void setConnector(String connector) {
        this.connector = connector;
    }

    @JsonProperty("branchId")
    public Integer getBranchId() {
        return branchId;
    }

    @JsonProperty("branchId")
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    @JsonProperty("services")
    public List<String> getServices() {
        return services;
    }

    @JsonProperty("services")
    public void setServices(List<String> services) {
        this.services = services;
    }

    @JsonProperty("ipAddress")
    public String getIpAddress() {
        return ipAddress;
    }

    @JsonProperty("ipAddress")
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("startTime")
    public String getStartTime() {
        return startTime;
    }

    @JsonProperty("startTime")
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @JsonProperty("refreshCycleCount")
    public Integer getRefreshCycleCount() {
        return refreshCycleCount;
    }

    @JsonProperty("refreshCycleCount")
    public void setRefreshCycleCount(Integer refreshCycleCount) {
        this.refreshCycleCount = refreshCycleCount;
    }

    @JsonProperty("subType")
    public String getSubType() {
        return subType;
    }

    @JsonProperty("subType")
    public void setSubType(String subType) {
        this.subType = subType;
    }

    @JsonProperty("branch-maintenance-mode")
    public Boolean getBranchMaintenanceMode() {
        return branchMaintenanceMode;
    }

    @JsonProperty("branch-maintenance-mode")
    public void setBranchMaintenanceMode(Boolean branchMaintenanceMode) {
        this.branchMaintenanceMode = branchMaintenanceMode;
    }

    @JsonProperty("controllers")
    public List<String> getControllers() {
        return controllers;
    }

    @JsonProperty("controllers")
    public void setControllers(List<String> controllers) {
        this.controllers = controllers;
    }
}