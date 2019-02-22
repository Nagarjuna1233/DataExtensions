package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Option implements Serializable {

    private static final long serialVersionUID = -2620083927063511616L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("tag")
    private Integer tag;

    @JsonProperty("metric")
    private Integer metric;

    @JsonProperty("interface-name")
    private String interfaceName;

    @JsonProperty("next-hop-ip")
    private String nextHopIp;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("tag")
    public Integer getTag() {
        return tag;
    }

    @JsonProperty("tag")
    public void setTag(Integer tag) {
        this.tag = tag;
    }

    @JsonProperty("metric")
    public Integer getMetric() {
        return metric;
    }

    @JsonProperty("metric")
    public void setMetric(Integer metric) {
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

    @JsonProperty("next-hop-ip")
    public String getNextHopIp() {
        return nextHopIp;
    }

    @JsonProperty("next-hop-ip")
    public void setNextHopIp(String nextHopIp) {
        this.nextHopIp = nextHopIp;
    }

}