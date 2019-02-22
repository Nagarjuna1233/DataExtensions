package com.sap.tcl.avalon.versa.inbound.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.sap.tcl.avalon.utils.AvalonUtils;
import com.sap.tcl.avalon.versa.inbound.constants.VersaIntegrationConstants;
import com.sap.tcl.avalon.versa.inbound.dtos.PredefinedURLCategoriesResponse;
import com.sap.tcl.avalon.versa.inbound.dtos.UrlCategory;

import reactor.util.CollectionUtils;

public class PreUrlCategoryCallable implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(PreUrlCategoryCallable.class);
    private String preUrlsCategories;
    private VersaApi versaApi;
    private MessageChannel rawFragmentMessageInputChannel;
    private String dirId;

    public PreUrlCategoryCallable(String preUrlsCategories, VersaApi versaApi,
            MessageChannel rawFragmentMessageInputChannel, String dirId) {
        super();
        this.preUrlsCategories = preUrlsCategories;
        this.versaApi = versaApi;
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
        this.dirId = dirId;
    }

    @Override
    public Integer call() throws Exception {
        if (getPreUrlCategoryData()) {
            return 1;
        }
        return 0;
    }

    public boolean getPreUrlCategoryData()  {

        String reString = null;
        boolean isCompleted = false;
        try {
            reString = versaApi.versaGetCall(preUrlsCategories, new HashMap<String, String>(), String.class);
            LOG.info(reString);
        } catch (Exception e) {
            LOG.error("Error While Pulling {}  data,cause is {}", VersaIntegrationConstants.URL_CATEGORY_RAW,
                    e);
        }
        if (StringUtils.isNotBlank(reString)) {
            PredefinedURLCategoriesResponse predefinedURLCategoriesResponse = null;
            try {
                predefinedURLCategoriesResponse = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                        PredefinedURLCategoriesResponse.class);
            } catch (Exception e) {
                LOG.error("Error While Converting {} data,Cause is", VersaIntegrationConstants.URL_CATEGORY_RAW,
                        e);
            }
            if (predefinedURLCategoriesResponse != null
                    && !CollectionUtils.isEmpty(predefinedURLCategoriesResponse.getUrlCategory())) {
                Map<String, String> eachRecord = null;
                List<Map<String, String>> response = new ArrayList<>();
                for (UrlCategory urlCategory : predefinedURLCategoriesResponse.getUrlCategory()) {
                    eachRecord = new LinkedHashMap<>();

                    eachRecord.put("uid", this.dirId + "_" + urlCategory.getCategoryName());
                    eachRecord.put("url-category_category-name", urlCategory.getCategoryName());
                    eachRecord.put("url-category_description", urlCategory.getDescription());
                    eachRecord.put("definitionType", "PREDEFINED");
                    eachRecord.put("poolName", this.dirId);
                    response.add(eachRecord);
                }
                isCompleted = this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(
                        response, AvalonUtils.constructMessageHeader(VersaIntegrationConstants.URL_CATEGORY_RAW,
                                this.dirId + "_" + VersaIntegrationConstants.URL_CATEGORY_FEED)));
            }
        } else {
            LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, preUrlsCategories);
            isCompleted = true;
        }
        return isCompleted;

    }
}
