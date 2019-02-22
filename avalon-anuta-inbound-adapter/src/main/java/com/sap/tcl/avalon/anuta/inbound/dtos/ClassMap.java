package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "name", "class-match-condition" })
public class ClassMap implements Serializable {

    private static final long serialVersionUID = -4717492327460399492L;
    @JsonProperty("name")
    private String name;
    @JsonProperty("class-match-condition")
    private List<ClassMatchCondition> classMatchCondition;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("class-match-condition")
    public List<ClassMatchCondition> getClassMatchCondition() {
        return classMatchCondition;
    }

    @JsonProperty("class-match-condition")
    public void setClassMatchCondition(List<ClassMatchCondition> classMatchCondition) {
        this.classMatchCondition = classMatchCondition;
    }
}