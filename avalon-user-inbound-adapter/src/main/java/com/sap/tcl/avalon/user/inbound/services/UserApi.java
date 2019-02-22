package com.sap.tcl.avalon.user.inbound.services;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.sap.tcl.avalon.rest.filters.AvalonLoggerClientFilter;
import com.sap.tcl.avalon.rest.services.AvalonClientConstants;
import com.sap.tcl.avalon.rest.services.AvalonJerseyClient;
import com.sap.tcl.avalon.utils.AvalonUtils;

public class UserApi {
    private static final Logger LOG = LoggerFactory.getLogger(UserApi.class);

    private String userId;
    private String password;
    private String baseUrl;

    @SuppressWarnings("unchecked")
    public <T> T userGetCall(String url, Map<String, String> paramKeyValues,
            final Class<T> responseType) {
        String url1 = this.baseUrl + url;
        String response = null;
        LOG.info("URL :::::  {}  user :  {}  pass :  {} ", url1, userId,
                password);
        try {
            response = (String) new AvalonJerseyClient.AvaloneHttpsBuilder(
                    AvalonUtils.buildPathParamUrl(url1, paramKeyValues))
                    .setAccept(MediaType.APPLICATION_JSON)
                    .setLogger(AvalonLoggerClientFilter.class)
                    .setBasicAuthentication(this.userId, this.password).build()
                    .get(responseType,"HANA",AvalonUtils.buildPathParamUrl(url1, paramKeyValues),"Error While Pulling");
        } catch (Exception e) {
            LOG.error("Error in URL", e);
        }

        return (T) response;
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
