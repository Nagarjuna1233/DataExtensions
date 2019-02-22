package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "id", "device-owner", "name", "next-hop-ip", "tag", "matric", "interfaceName" })
public class AllDevicesOption implements Serializable {

    private static final long serialVersionUID = -5528501494283299714L;
    @JsonProperty("id")
    private String id;
    @JsonProperty("@")
    private DeviceOwner deviceOwner;
    @JsonProperty("name")
    private String name;
    @JsonProperty("next-hop-ip")
    private String nextHopIp;

    @JsonProperty("tag")
    private String tag;
    @JsonProperty("metric")
    private String metric;
    @JsonProperty("interface-name")
    private String interfaceName;

    @JsonProperty("tag")
    public String getTag() {
        return tag;
    }

    @JsonProperty("tag")
    public void setTag(String tag) {
        this.tag = tag;
    }

    @JsonProperty("metric")
    public String getMetric() {
        return metric;
    }

    @JsonProperty("metric")
    public void setMetric(String metric) {
        this.metric = metric;
    }

    @JsonProperty("interface-name")
    public String getInterfaceName() {
        return interfaceName;
    }

    @JsonProperty("interface-name")
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("@")
    public DeviceOwner getDeviceOwner() {
        return deviceOwner;
    }

    @JsonProperty("@")
    public void setDeviceOwner(DeviceOwner deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("next-hop-ip")
    public String getNextHopIp() {
        return nextHopIp;
    }

    @JsonProperty("next-hop-ip")
    public void setNextHopIp(String nextHopIp) {
        this.nextHopIp = nextHopIp;
    }
}