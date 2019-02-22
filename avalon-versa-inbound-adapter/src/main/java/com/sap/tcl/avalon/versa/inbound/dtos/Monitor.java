package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Monitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("interval")
    private String interval;

    @JsonProperty("threshold")
    private String threshold;

    @JsonProperty("interval")
    public String getInterval() {
        return interval;
    }

    @JsonProperty("interval")
    public void setInterval(String interval) {
        this.interval = interval;
    }

    @JsonProperty("threshold")
    public String getThreshold() {
        return threshold;
    }

    @JsonProperty("threshold")
    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

}
