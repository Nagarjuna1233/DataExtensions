package com.sap.tcl.avalon.user.inbound.services;

import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sap.tcl.avalon.exceptions.ResoucePullingFailedException;
import com.sap.tcl.avalon.utils.AvalonUtils;

public final class UserProductPullServiceWorker {

    private static UserApi userApi;
    private static String rootElement;
    private static String subRootElement;
    private static String skippingElement;

    private static final Logger LOG = LoggerFactory
            .getLogger(UserProductPullServiceWorker.class);

    private UserProductPullServiceWorker() {

    }

    public static JSONArray getCustomerEntities(String url, String rawInterface) {

        JSONArray rawdata = null;

        String customerEntities = null;
        try {

            customerEntities = getUserApi().userGetCall(url,
                    new HashMap<String, String>(), String.class);

        } catch (Exception e) {

            try {
                throw new ResoucePullingFailedException(rawInterface,
                        AvalonUtils.buildPathParamUrl(url, null),
                        e.getMessage(), e);
            } catch (ResoucePullingFailedException e1) {
                LOG.error("Resouce Pulling Failed ..........", e1);
            }
        }
        if (StringUtils.isNotBlank(customerEntities)) {

            LOG.info("reString from hana   :  {} ", customerEntities);

            rawdata = convertToDataHubInput(new JSONObject(customerEntities));

        }
        return rawdata;
    }

    private static JSONArray convertToDataHubInput(JSONObject customerEntities) {
        JSONObject customerRootElement = (JSONObject) customerEntities
                .get(getRootElement());
        return (JSONArray) customerRootElement.get(getSubRootElement());

    }

    private static String getSubRootElement() {
        return subRootElement;
    }

    private static String getRootElement() {

        return rootElement;
    }

    public static void setRootElement(String rootElement) {
        UserProductPullServiceWorker.rootElement = rootElement;
    }

    public static void setSubRootElement(String subRootElement) {
        UserProductPullServiceWorker.subRootElement = subRootElement;
    }

    public static UserApi getUserApi() {
        return userApi;
    }

    public static void setUserApi(UserApi userApi) {
        UserProductPullServiceWorker.userApi = userApi;
    }

    public static String getSkippingElement() {
        return skippingElement;
    }

    public static void setSkippingElement(String skippingElement) {
        UserProductPullServiceWorker.skippingElement = skippingElement;
    }

}
