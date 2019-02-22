package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "class-map" })
public class QosClassMaps implements Serializable {

    private static final long serialVersionUID = 2010961273592906663L;
    @JsonProperty("class-map")
    private List<ClassMap> classMap;

    @JsonProperty("class-map")
    public List<ClassMap> getClassMap() {
        return classMap;
    }

    @JsonProperty("class-map")
    public void setClassMap(List<ClassMap> classMap) {
        this.classMap = classMap;
    }
}