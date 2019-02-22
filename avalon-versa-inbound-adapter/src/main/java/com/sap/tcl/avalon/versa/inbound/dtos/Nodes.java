package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "nodeStatusList" })
public class Nodes implements Serializable {

    private static final long serialVersionUID = 7405750944571633030L;
    @JsonProperty("nodeStatusList")
    private NodeStatusList nodeStatusList;

    @JsonProperty("nodeStatusList")
    public NodeStatusList getNodeStatusList() {
        return nodeStatusList;
    }

    @JsonProperty("nodeStatusList")
    public void setNodeStatusList(NodeStatusList nodeStatusList) {
        this.nodeStatusList = nodeStatusList;
    }
}