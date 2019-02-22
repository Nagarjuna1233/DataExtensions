package com.sap.tcl.avalon.versa.inbound.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDetailsAsset {

    @JsonProperty("name")
    private String name;
    @JsonProperty("serial-no")
    private String serialNo;
    @JsonProperty("organization")
    private String organization;
    @JsonProperty("location")
    private String location;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("site-name")
    private String siteName;
    @JsonProperty("site-id")
    private Integer siteId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("altitude")
    private String altitude;
    @JsonProperty("device-type")
    private String deviceType;

    @JsonProperty("altitude")
    public String getAltitude() {
        return altitude;
    }

    @JsonProperty("altitude")
    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    @JsonProperty("device-type")
    public String getDeviceType() {
        return deviceType;
    }

    @JsonProperty("device-type")
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("serial-no")
    public String getSerialNo() {
        return serialNo;
    }

    @JsonProperty("serial-no")
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @JsonProperty("organization")
    public String getOrganization() {
        return organization;
    }

    @JsonProperty("organization")
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("latitude")
    public String getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public String getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("site-name")
    public String getSiteName() {
        return siteName;
    }

    @JsonProperty("site-name")
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @JsonProperty("site-id")
    public Integer getSiteId() {
        return siteId;
    }

    @JsonProperty("site-id")
    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }
}
