package com.sap.tcl.avalon.versa.inbound.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APPQOSPolicyUpdateUrlCategory extends APPQOSRulesUrlCategory implements Serializable {

    private static final long serialVersionUID = 1L;

}
