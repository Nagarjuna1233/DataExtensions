package com.sap.tcl.avalon.user.inbound.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Async;

import com.sap.tcl.avalon.user.inbound.constants.UserIntegrationConstants;
import com.sap.tcl.avalon.utils.AvalonUtils;

public class UserPullServiceWorker {

    static final String TRUEVALUE = "true";
    static final String FALSEVALUE = "false";
    static final String DEFAULTVALUE = "null";
    private MessageChannel rawFragmentMessageInputChannel;
    private String customerMasterUrl;
    private String isPartner;
    private String isCustomer;
    private String isPartnerValue;
    private String isCustomerValue;
    private String owner;
    private String idType;
    private String idTypeValue;
    private String accountId;
    private String accountOwner;
    private String leOwner;
    private String cUID;
    private String countryName;

    private static final Logger LOG = LoggerFactory
            .getLogger(UserPullServiceWorker.class);

    public void pull() {
        try {
            processUserData(customerMasterUrl,
                    UserIntegrationConstants.RAW_CUSTOMER_MASTER,
                    UserIntegrationConstants.USER_DEFAULT_FEED_NAME);

        } catch (Exception e) {
            LOG.error("Error in pulling data.....", e);
        }
    }

    @Async
    public void processUserData(String url, String rawInterface, String feedName) {
        JSONArray customerSubRootElement = UserProductPullServiceWorker
                .getCustomerEntities(url, rawInterface);

        List<Map<String, String>> rawdata = convertToRawdata(customerSubRootElement);

        this.rawFragmentMessageInputChannel
                .send(new GenericMessage<List<Map<String, String>>>(rawdata,
                        AvalonUtils.constructMessageHeader(rawInterface,
                                feedName)));
    }

    @SuppressWarnings("rawtypes")
    public List<Map<String, String>> convertToRawdata(JSONArray array) {
        @SuppressWarnings("unchecked")
        List<Map<String, String>> list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONObject) {
                list.add(prepareCustomerRawData((JSONObject) value));
            }
        }

        return list;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map<String, String> prepareCustomerRawData(JSONObject object) {
        Map<String, String> map = new HashMap();
        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();

            String value = String.valueOf(object.get(key).toString());
            if (key.contains(UserProductPullServiceWorker.getSkippingElement())) {
                continue;
            }
            if (key.equals(getCountryName())) {
                String isoCode = getCountryCode(value);
                map.put(key, value);
                map.put("ISO_CODE", isoCode);

            }

            if (key.equals(getIsPartner()) || key.equals(getIsCustomer())) {
                String response = getCustomerPartner(value);
                map.put(key, response);
            } else {
                map.put(key, value);
            }
        }
        LOG.info("{}", map);
        if (map.get(getIdType()).equals(getIdTypeValue())) {
            map.put(getIdType(), "ACCOUNT");
            map.put(getcUID(), map.get(getAccountId()));
            map.put(getAccountId(), "");
            map.put(getOwner(), map.get(getAccountOwner()));
        } else {
            map.put(getIdType(), "LEGALENTITY");
            map.put(getOwner(), map.get(getAccountOwner()));
        }
        return map;
    }

    public String getCountryCode(String value) {
        String isoCode;

        if (!value.equals(DEFAULTVALUE)) {
            String ch = value.substring(0, 2);
            isoCode = ch.toUpperCase();

        } else {
            isoCode = value;
        }
        return isoCode;
    }

    public String getCustomerPartner(String value) {
        String response;
        if (value.equalsIgnoreCase(getIsPartnerValue())
                || value.equalsIgnoreCase(getIsCustomerValue())) {
            response = TRUEVALUE;
        } else {
            response = FALSEVALUE;
        }
        return response;
    }

    public MessageChannel getRawFragmentMessageInputChannel() {
        return rawFragmentMessageInputChannel;
    }

    public void setRawFragmentMessageInputChannel(
            MessageChannel rawFragmentMessageInputChannel) {
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
    }

    public String getCustomerMasterUrl() {
        return customerMasterUrl;
    }

    public void setCustomerMasterUrl(String customerMasterUrl) {
        this.customerMasterUrl = customerMasterUrl;
    }

    public String getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(String isPartner) {
        this.isPartner = isPartner;
    }

    public String getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(String isCustomer) {
        this.isCustomer = isCustomer;
    }

    public String getIsPartnerValue() {
        return isPartnerValue;
    }

    public void setIsPartnerValue(String isPartnerValue) {
        this.isPartnerValue = isPartnerValue;
    }

    public String getIsCustomerValue() {
        return isCustomerValue;
    }

    public void setIsCustomerValue(String isCustomerValue) {
        this.isCustomerValue = isCustomerValue;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdTypeValue() {
        return idTypeValue;
    }

    public void setIdTypeValue(String idTypeValue) {
        this.idTypeValue = idTypeValue;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public String getLeOwner() {
        return leOwner;
    }

    public void setLeOwner(String leOwner) {
        this.leOwner = leOwner;
    }

    public String getcUID() {
        return cUID;
    }

    public void setcUID(String cUID) {
        this.cUID = cUID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
