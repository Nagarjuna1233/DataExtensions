package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "count", "template" })
public class VersanmsTemplates implements Serializable {

    private static final long serialVersionUID = 4850408856794989256L;

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("template")
    private List<Template> template;

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("template")
    public List<Template> getTemplate() {
        return template;
    }

    @JsonProperty("template")
    public void setTemplate(List<Template> template) {
        this.template = template;
    }

}