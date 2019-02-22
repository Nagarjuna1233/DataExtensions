package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
"slot-id",
"vm-name",
"vm-status",
"node-type",
"host-ip",
"cpu-load",
"memory-load",
"load-factor"
})
public class NodeStatusList implements Serializable{

@JsonProperty("slot-id")
private Integer slotId;
@JsonProperty("vm-name")
private String vmName;
@JsonProperty("vm-status")
private String vmStatus;
@JsonProperty("node-type")
private String nodeType;
@JsonProperty("host-ip")
private String hostIp;
@JsonProperty("cpu-load")
private Integer cpuLoad;
@JsonProperty("memory-load")
private Integer memoryLoad;
@JsonProperty("load-factor")
private Integer loadFactor;

@JsonProperty("slot-id")
public Integer getSlotId() {
return slotId;
}

@JsonProperty("slot-id")
public void setSlotId(Integer slotId) {
this.slotId = slotId;
}

@JsonProperty("vm-name")
public String getVmName() {
return vmName;
}

@JsonProperty("vm-name")
public void setVmName(String vmName) {
this.vmName = vmName;
}

@JsonProperty("vm-status")
public String getVmStatus() {
return vmStatus;
}

@JsonProperty("vm-status")
public void setVmStatus(String vmStatus) {
this.vmStatus = vmStatus;
}

@JsonProperty("node-type")
public String getNodeType() {
return nodeType;
}

@JsonProperty("node-type")
public void setNodeType(String nodeType) {
this.nodeType = nodeType;
}

@JsonProperty("host-ip")
public String getHostIp() {
return hostIp;
}

@JsonProperty("host-ip")
public void setHostIp(String hostIp) {
this.hostIp = hostIp;
}

@JsonProperty("cpu-load")
public Integer getCpuLoad() {
return cpuLoad;
}

@JsonProperty("cpu-load")
public void setCpuLoad(Integer cpuLoad) {
this.cpuLoad = cpuLoad;
}

@JsonProperty("memory-load")
public Integer getMemoryLoad() {
return memoryLoad;
}

@JsonProperty("memory-load")
public void setMemoryLoad(Integer memoryLoad) {
this.memoryLoad = memoryLoad;
}

@JsonProperty("load-factor")
public Integer getLoadFactor() {
return loadFactor;
}

@JsonProperty("load-factor")
public void setLoadFactor(Integer loadFactor) {
this.loadFactor = loadFactor;
}
}