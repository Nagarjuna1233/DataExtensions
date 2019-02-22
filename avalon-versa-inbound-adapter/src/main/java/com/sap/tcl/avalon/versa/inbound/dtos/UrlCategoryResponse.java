package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ " url-category" })
public class UrlCategoryResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("url-category")
    private List<UrlCategory> urlCategory;

    @JsonProperty("url-category")
    public List<UrlCategory> getUrlCategory() {
        return urlCategory;
    }

    @JsonProperty("url-category")
    public void setUrlCategory(List<UrlCategory> urlCategory) {
        this.urlCategory = urlCategory;
    }

}
