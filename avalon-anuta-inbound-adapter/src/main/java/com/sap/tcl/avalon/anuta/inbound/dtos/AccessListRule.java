package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "source-object", "source-condition", "protocol", "destination-condition", "cascade", "port-number",
        "destination-object" })
public class AccessListRule implements Serializable {

    private static final long serialVersionUID = 2409625704524097644L;

    @JsonProperty("source-object")
    private String sourceObject;
    @JsonProperty("source-condition")
    private String sourceCondition;
    @JsonProperty("protocol")
    private String protocol;
    @JsonProperty("destination-condition")
    private String destinationCondition;
    @JsonProperty("cascade")
    private Cascade cascade;
    @JsonProperty("port-number")
    private Integer portNumber;
    @JsonProperty("destination-object")
    private String destinationObject;

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

    @JsonProperty("source-object")
    public String getSourceObject() {
        return sourceObject;
    }

    @JsonProperty("source-object")
    public void setSourceObject(String sourceObject) {
        this.sourceObject = sourceObject;
    }

    @JsonProperty("source-condition")
    public String getSourceCondition() {
        return sourceCondition;
    }

    @JsonProperty("source-condition")
    public void setSourceCondition(String sourceCondition) {
        this.sourceCondition = sourceCondition;
    }

    @JsonProperty("protocol")
    public String getProtocol() {
        return protocol;
    }

    @JsonProperty("protocol")
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @JsonProperty("destination-condition")
    public String getDestinationCondition() {
        return destinationCondition;
    }

    @JsonProperty("destination-condition")
    public void setDestinationCondition(String destinationCondition) {
        this.destinationCondition = destinationCondition;
    }

    @JsonProperty("cascade")
    public Cascade getCascade() {
        return cascade;
    }

    @JsonProperty("cascade")
    public void setCascade(Cascade cascade) {
        this.cascade = cascade;
    }

    @JsonProperty("port-number")
    public Integer getPortNumber() {
        return portNumber;
    }

    @JsonProperty("port-number")
    public void setPortNumber(Integer portNumber) {
        this.portNumber = portNumber;
    }

    @JsonProperty("destination-object")
    public String getDestinationObject() {
        return destinationObject;
    }

    @JsonProperty("destination-object")
    public void setDestinationObject(String destinationObject) {
        this.destinationObject = destinationObject;
    }
}