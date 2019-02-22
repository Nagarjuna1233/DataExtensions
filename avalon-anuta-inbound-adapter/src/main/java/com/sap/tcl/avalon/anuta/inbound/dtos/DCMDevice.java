package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "id", "device-class-map", "qos:class-maps" })
public class DCMDevice implements Serializable {

    private static final long serialVersionUID = -4113406367676105218L;
    @JsonProperty("id")
    private String id;
    @JsonProperty("device-class-map")
    private DeviceClassMap deviceClassMap;
    @JsonProperty("qos:class-maps")
    private QosClassMaps qosClassMaps;

    @JsonProperty("@")
    private DeviceOwner deviceOwner;

    @JsonProperty("@")
    public DeviceOwner getDeviceOwner() {
        return deviceOwner;
    }

    @JsonProperty("@")
    public void setDeviceOwner(DeviceOwner deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("device-class-map")
    public DeviceClassMap getDeviceClassMap() {
        return deviceClassMap;
    }

    @JsonProperty("device-class-map")
    public void setDeviceClassMap(DeviceClassMap deviceClassMap) {
        this.deviceClassMap = deviceClassMap;
    }

    @JsonProperty("qos:class-maps")
    public QosClassMaps getQosClassMaps() {
        return qosClassMaps;
    }

    @JsonProperty("qos:class-maps")
    public void setQosClassMaps(QosClassMaps qosClassMaps) {
        this.qosClassMaps = qosClassMaps;
    }
}