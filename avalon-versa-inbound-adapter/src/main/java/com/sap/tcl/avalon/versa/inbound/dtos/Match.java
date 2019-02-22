package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match implements Serializable{

@JsonProperty("url-category")
private UrlCategory urlCategory;
@JsonProperty("ip-version")
private String ipVersion;
@JsonProperty("ip-flags")
private String ipFlags;
@JsonProperty("application")
private Application application;
@JsonProperty("source")
private Source source;
@JsonProperty("destination")
private Destination destination;

@JsonProperty("url-category")
public UrlCategory getUrlCategory() {
return urlCategory;
}

@JsonProperty("url-category")
public void setUrlCategory(UrlCategory urlCategory) {
this.urlCategory = urlCategory;
}

@JsonProperty("ip-version")
public String getIpVersion() {
return ipVersion;
}

@JsonProperty("ip-version")
public void setIpVersion(String ipVersion) {
this.ipVersion = ipVersion;
}

@JsonProperty("ip-flags")
public String getIpFlags() {
return ipFlags;
}

@JsonProperty("ip-flags")
public void setIpFlags(String ipFlags) {
this.ipFlags = ipFlags;
}

@JsonProperty("application")
public Application getApplication() {
return application;
}

@JsonProperty("application")
public void setApplication(Application application) {
this.application = application;
}

@JsonProperty("source")
public Source getSource() {
return source;
}

@JsonProperty("source")
public void setSource(Source source) {
this.source = source;
}

@JsonProperty("destination")
public Destination getDestination() {
return destination;
}

@JsonProperty("destination")
public void setDestination(Destination destination) {
this.destination = destination;
}



}
