package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "connect", "sync", "sync-to", "sync-from", "check-sync", "check-yang-modules",
        "fetch-ssh-host-keys", "apply-template" })
public class Operations implements Serializable {

    private static final long serialVersionUID = -6668061419447562187L;
    @JsonProperty("connect")
    private String connect;
    @JsonProperty("sync")
    private String sync;
    @JsonProperty("sync-to")
    private String syncTo;
    @JsonProperty("sync-from")
    private String syncFrom;
    @JsonProperty("check-sync")
    private String checkSync;
    @JsonProperty("check-yang-modules")
    private String checkYangModules;
    @JsonProperty("fetch-ssh-host-keys")
    private String fetchSshHostKeys;
    @JsonProperty("apply-template")
    private String applyTemplate;

    @JsonProperty("connect")
    public String getConnect() {
        return connect;
    }

    @JsonProperty("connect")
    public void setConnect(String connect) {
        this.connect = connect;
    }

    @JsonProperty("sync")
    public String getSync() {
        return sync;
    }

    @JsonProperty("sync")
    public void setSync(String sync) {
        this.sync = sync;
    }

    @JsonProperty("sync-to")
    public String getSyncTo() {
        return syncTo;
    }

    @JsonProperty("sync-to")
    public void setSyncTo(String syncTo) {
        this.syncTo = syncTo;
    }

    @JsonProperty("sync-from")
    public String getSyncFrom() {
        return syncFrom;
    }

    @JsonProperty("sync-from")
    public void setSyncFrom(String syncFrom) {
        this.syncFrom = syncFrom;
    }

    @JsonProperty("check-sync")
    public String getCheckSync() {
        return checkSync;
    }

    @JsonProperty("check-sync")
    public void setCheckSync(String checkSync) {
        this.checkSync = checkSync;
    }

    @JsonProperty("check-yang-modules")
    public String getCheckYangModules() {
        return checkYangModules;
    }

    @JsonProperty("check-yang-modules")
    public void setCheckYangModules(String checkYangModules) {
        this.checkYangModules = checkYangModules;
    }

    @JsonProperty("fetch-ssh-host-keys")
    public String getFetchSshHostKeys() {
        return fetchSshHostKeys;
    }

    @JsonProperty("fetch-ssh-host-keys")
    public void setFetchSshHostKeys(String fetchSshHostKeys) {
        this.fetchSshHostKeys = fetchSshHostKeys;
    }

    @JsonProperty("apply-template")
    public String getApplyTemplate() {
        return applyTemplate;
    }

    @JsonProperty("apply-template")
    public void setApplyTemplate(String applyTemplate) {
        this.applyTemplate = applyTemplate;
    }
}
