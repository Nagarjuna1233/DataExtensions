package com.sap.tcl.avalon.versa.inbound.services;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.sap.tcl.avalon.exceptions.ResoucePullingFailedException;
import com.sap.tcl.avalon.rest.filters.AvalonLoggerClientFilter;
import com.sap.tcl.avalon.rest.services.AvalonClientConstants;
import com.sap.tcl.avalon.rest.services.AvalonJerseyClient;
import com.sap.tcl.avalon.utils.AvalonUtils;

public class VersaApi {

    private static final Logger LOG = LoggerFactory.getLogger(VersaApi.class);
    
    private String userId;
    private String password;
    private String baseUrl;

    public <T> T versaGetCall(String url, Map<String, String> paramKeyValues, final Class<T> responseType)throws ResoucePullingFailedException {
        try {
            return new AvalonJerseyClient.AvaloneHttpsBuilder(
                    AvalonUtils.buildPathParamUrl(this.baseUrl + url, paramKeyValues)).setAccept(MediaType.APPLICATION_JSON)
                            .setLogger(AvalonLoggerClientFilter.class).setBasicAuthentication(this.userId, this.password)
                            .build().get(responseType,"VERSA", AvalonUtils.buildPathParamUrl(this.baseUrl + url, paramKeyValues),"Error While Pulling");
        } catch (Exception e) {
            LOG.error("Error While pulling from url {},Cause is {}",AvalonUtils.buildPathParamUrl(this.baseUrl + url, paramKeyValues),e);
        } 
        return null;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    @Required
    public void setBaseUrl(String baseUrl) {
        if (baseUrl.endsWith(AvalonClientConstants.FORWARD_SLASH)) {
            this.baseUrl = baseUrl;
        } else {
            this.baseUrl = baseUrl + AvalonClientConstants.FORWARD_SLASH;
        }
    }

}
