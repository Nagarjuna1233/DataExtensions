package com.sap.tcl.avalon.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class AvalonUtils {

    private static final Logger LOG = LoggerFactory.getLogger(AvalonUtils.class);
    protected static final Map<String, String> EMPTY_MAP = new HashMap<>();

    private AvalonUtils() {

    }

    public static String buildPathParamUrl(String url, Map<String, String> pathParamKeyValues) {
        Map<String, String> pathParams = new HashMap<>();
        if (pathParamKeyValues != null) {
            pathParams.putAll(pathParamKeyValues);
        }
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).buildAndExpand(pathParams);
        LOG.info("Build url ", uri.toUriString());
        return uri.toUriString();
    }

    protected static void convert(Map<String, Object> source, String saperator, String listValueSaperator,
            String rootName, List<Map<String, String>> resultMap, Map<String, String> customePropertis) {
        Map<String, String> result = new HashMap<String, String>();
        Collection<Object> collectObjts;
        String key;
        for (Entry<String, Object> set : source.entrySet()) {
            key = set.getKey();
            if (set.getValue() instanceof Map) {
                convert((Map<String, Object>) set.getValue(), saperator, listValueSaperator, rootName + key + saperator,
                        resultMap, customePropertis);
            } else if (set.getValue() instanceof Collection) {
                collectObjts = (Collection<Object>) set.getValue();
                if (isCollectionOfPrimetive(collectObjts, saperator, listValueSaperator, rootName + key, resultMap,
                        customePropertis)) {
                    result.put(rootName + key, listToString(collectObjts, listValueSaperator));
                }
            } else {
                result.put(rootName + key, String.valueOf(set.getValue()));
            }
        }
        if (result.size() > 0) {
            if (!CollectionUtils.isEmpty(customePropertis)) {
                for (Entry<String, String> cProperties : customePropertis.entrySet()) {
                    result.put(cProperties.getKey(), cProperties.getValue());
                }
            }
            resultMap.add(result);
        }

    }

    /**
     * 
     * @param source
     * @param saperator
     * @param listValueSaperator
     * @param rootName
     * @param resultMap
     * @param customePropertis
     * @return
     */
    protected static boolean isCollectionOfPrimetive(Collection<Object> source, String saperator,
            String listValueSaperator, String rootName, List<Map<String, String>> resultMap,
            Map<String, String> customePropertis) {
        Iterator<Object> iter = source.iterator();
        while (iter.hasNext()) {
            Object value = iter.next();
            if (value instanceof Map) {
                convert((Map<String, Object>) value, saperator, listValueSaperator, rootName + saperator, resultMap,
                        customePropertis);
            } else if (value instanceof Collection) {
                isCollectionOfPrimetive((List<Object>) value, saperator, listValueSaperator, rootName, resultMap,
                        customePropertis);
            } else {
                return true;
            }
        }
        return false;

    }

    /**
     * Method to convert List to string
     * 
     * @param list
     * @param delimeter
     * @return
     */
    public static String listToString(Collection<Object> list, String delimeter) {
        StringBuilder csvBuilder = new StringBuilder("");
        if (!CollectionUtils.isEmpty(list)) {
            Iterator<Object> strIterator = list.iterator();
            int count = 1;
            int listSize = list.size();
            while (strIterator.hasNext()) {
                String value = String.valueOf(strIterator.next());
                csvBuilder.append(value);
                if (count < listSize) {
                    csvBuilder.append(delimeter);
                }
                count++;
            }
        }
        return csvBuilder.toString();
    }

    /**
     * Method to convert Headers
     * 
     * @param itemType
     * @param feedName
     * @return
     */
    public static Map<String, Object> constructMessageHeader(final String itemType, final String feedName) {
        final Map<String, Object> header = new HashMap<String, Object>();
        header.put("itemType", itemType);
        header.put("feedName", feedName);
        return header;
    }

    /**
     * Method to List from comma separated String
     * 
     * @param val
     * @return
     */
    public static List<String> getListValue(String val) {
        List<String> valueList = new ArrayList<>();
        if (!StringUtils.isEmpty(val)) {
            valueList = new ArrayList<>();
            String[] valuearray = val.split(",");
            for (int i = 0; i < valuearray.length; i++) {
                valueList.add(valuearray[i]);
            }
        }
        return valueList;
    }
}
