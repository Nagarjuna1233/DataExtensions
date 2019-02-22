package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "versanms.templates" })
public class VersanmstemplatesResponse implements Serializable {

    private static final long serialVersionUID = 4557827883915558237L;

    @JsonProperty("versanms.templates")
    private VersanmsTemplates versanmsTemplates;

    @JsonProperty("versanms.templates")
    public VersanmsTemplates getVersanmsTemplates() {
        return versanmsTemplates;
    }

    @JsonProperty("versanms.templates")
    public void setVersanmsTemplates(VersanmsTemplates versanmsTemplates) {
        this.versanmsTemplates = versanmsTemplates;
    }
}