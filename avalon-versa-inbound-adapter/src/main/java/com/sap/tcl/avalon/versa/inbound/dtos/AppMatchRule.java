package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "rule-name", "host-pattern", "source-prefix", "destination-prefix", "source-port",
        "destination-port" })
public class AppMatchRule implements Serializable {

    private static final long serialVersionUID = 1841940782937785333L;

    @JsonProperty("rule-name")
    private String ruleName;
    @JsonProperty("host-pattern")
    private String hostPattern;
    @JsonProperty("source-prefix")
    private String sourcePrefix;
    @JsonProperty("destination-prefix")
    private String destinationPrefix;
    @JsonProperty("source-port")
    private SourcePort sourcePort;
    @JsonProperty("destination-port")
    private DestinationPort destinationPort;

    @JsonProperty("rule-name")
    public String getRuleName() {
        return ruleName;
    }

    @JsonProperty("rule-name")
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    @JsonProperty("host-pattern")
    public String getHostPattern() {
        return hostPattern;
    }

    @JsonProperty("host-pattern")
    public void setHostPattern(String hostPattern) {
        this.hostPattern = hostPattern;
    }

    @JsonProperty("source-prefix")
    public String getSourcePrefix() {
        return sourcePrefix;
    }

    @JsonProperty("source-prefix")
    public void setSourcePrefix(String sourcePrefix) {
        this.sourcePrefix = sourcePrefix;
    }

    @JsonProperty("destination-prefix")
    public String getDestinationPrefix() {
        return destinationPrefix;
    }

    @JsonProperty("destination-prefix")
    public void setDestinationPrefix(String destinationPrefix) {
        this.destinationPrefix = destinationPrefix;
    }

    @JsonProperty("source-port")
    public SourcePort getSourcePort() {
        return sourcePort;
    }

    @JsonProperty("source-port")
    public void setSourcePort(SourcePort sourcePort) {
        this.sourcePort = sourcePort;
    }

    @JsonProperty("destination-port")
    public DestinationPort getDestinationPort() {
        return destinationPort;
    }

    @JsonProperty("destination-port")
    public void setDestinationPort(DestinationPort destinationPort) {
        this.destinationPort = destinationPort;
    }

}