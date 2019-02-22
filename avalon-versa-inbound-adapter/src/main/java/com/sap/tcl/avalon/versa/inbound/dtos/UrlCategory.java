package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "category-name" })
public class UrlCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("category-name")
    private String categoryName;

    @JsonProperty("predefined")
    private List<String> predefined;
    @JsonProperty("user-defined")
    private List<String> userDefined;
    @JsonProperty("description")
    private String description;

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("category-name")
    public String getCategoryName() {
        return categoryName;
    }

    @JsonProperty("category-name")
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @JsonProperty("predefined")
    public List<String> getPredefined() {
        return predefined;
    }

    @JsonProperty("predefined")
    public void setPredefined(List<String> predefined) {
        this.predefined = predefined;
    }

    @JsonProperty("user-defined")
    public List<String> getUserDefined() {
        return userDefined;
    }

    @JsonProperty("user-defined")
    public void setUserDefined(List<String> userDefined) {
        this.userDefined = userDefined;
    }
}