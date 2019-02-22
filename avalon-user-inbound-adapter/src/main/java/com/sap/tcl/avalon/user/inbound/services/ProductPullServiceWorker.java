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

public class ProductPullServiceWorker {

    private MessageChannel rawFragmentMessageInputChannel;
    private String productMasterUrl;
    private String cuid;

    private static final Logger LOG = LoggerFactory
            .getLogger(ProductPullServiceWorker.class);

    public void pull() {

        LOG.info("Pulling data .............");
        try {
            LOG.info(productMasterUrl);

            processProductData(productMasterUrl,
                    UserIntegrationConstants.RAW_PRODUCT_MASTER,
                    UserIntegrationConstants.USER_DEFAULT_FEED_NAME);
        } catch (Exception e) {
            LOG.error("Error in pulling data.....", e);

        }
    }

    @Async
    public void processProductData(String url, String rawInterface,
            String feedName) {

        JSONArray productSubRootElement = UserProductPullServiceWorker
                .getCustomerEntities(url, rawInterface);

        List<Map<String, String>> rawdata = convertToRawdata(productSubRootElement);

        this.rawFragmentMessageInputChannel
                .send(new GenericMessage<List<Map<String, String>>>(rawdata,
                        AvalonUtils.constructMessageHeader(rawInterface,
                                feedName)));

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<Map<String, String>> convertToRawdata(JSONArray array) {
        List<Map<String, String>> list = new ArrayList();

        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);

            if (value instanceof JSONObject) {
                list.add(prepareProductRawData((JSONObject) value));
            }
        }

        return list;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, String> prepareProductRawData(JSONObject object) {
        Map<String, String> map = new HashMap();

        Iterator<String> keysItr = object.keys();

        while (keysItr.hasNext()) {

            String key = keysItr.next();

            String value = String.valueOf(object.get(key).toString());

            if (key.contains(UserProductPullServiceWorker.getSkippingElement())) {
                continue;
            }
            if (key.equals(getCuid())) {
                map.put(key, value);
            } else {

                String product = value.replaceAll("\\s+", "");
                map.put(key, product);

            }
        }
        LOG.info("{}", map);

        return map;
    }

    public MessageChannel getRawFragmentMessageInputChannel() {
        return rawFragmentMessageInputChannel;
    }

    public void setRawFragmentMessageInputChannel(
            MessageChannel rawFragmentMessageInputChannel) {
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
    }

    public String getProductMasterUrl() {
        return productMasterUrl;
    }

    public void setProductMasterUrl(String productMasterUrl) {
        this.productMasterUrl = productMasterUrl;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }
}
