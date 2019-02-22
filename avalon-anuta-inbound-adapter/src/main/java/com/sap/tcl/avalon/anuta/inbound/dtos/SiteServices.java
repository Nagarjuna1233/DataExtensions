package com.sap.tcl.avalon.anuta.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteServices implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty("zero-touch-prov")
    private boolean zeroTouchProv;

    @JsonProperty("site-name")
    private String siteName;

    @JsonProperty("@")
    private DeviceOwner deviceOwner;

    @JsonProperty("cpe")
    private Cpe cpe;
    @JsonProperty("cpe-primary")
    private Cpe cpePrimary;
    @JsonProperty("cpe-secondary")
    private Cpe cpeSecondary;


    public Cpe getCpe() {
        return cpe;
    }

    public void setCpe(Cpe cpe) {
        this.cpe = cpe;
    }

    public Cpe getCpePrimary() {
        return cpePrimary;
    }

    public void setCpePrimary(Cpe cpePrimary) {
        this.cpePrimary = cpePrimary;
    }

    public Cpe getCpeSecondary() {
        return cpeSecondary;
    }

    public void setCpeSecondary(Cpe cpeSecondary) {
        this.cpeSecondary = cpeSecondary;
    }

    @JsonProperty("zero-touch-prov")
    public boolean isZeroTouchProv() {
        return zeroTouchProv;
    }

    @JsonProperty("zero-touch-prov")
    public void setZeroTouchProv(boolean zeroTouchProv) {
        this.zeroTouchProv = zeroTouchProv;
    }

    @JsonProperty("site-name")
    public String getSiteName() {
        return siteName;
    }

    @JsonProperty("site-name")
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @JsonProperty("@")
    public DeviceOwner getDeviceOwner() {
        return deviceOwner;
    }

    @JsonProperty("@")
    public void setDeviceOwner(DeviceOwner deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

}
