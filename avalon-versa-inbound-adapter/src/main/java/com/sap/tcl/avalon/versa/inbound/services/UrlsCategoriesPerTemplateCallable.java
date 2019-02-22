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
import com.sap.tcl.avalon.versa.inbound.dtos.Template;
import com.sap.tcl.avalon.versa.inbound.dtos.UrlCategory;
import com.sap.tcl.avalon.versa.inbound.dtos.UrlCategoryResponse;

import reactor.util.CollectionUtils;

public class UrlsCategoriesPerTemplateCallable implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(UrlsCategoriesPerTemplateCallable.class);

    private String urlsCategoriesPerTemplate;
    private String feedName;
    private VersaApi versaApi;
    private MessageChannel rawFragmentMessageInputChannel;
    private List<Template> templates;
    private String dirId;

    public UrlsCategoriesPerTemplateCallable(String urlsCategoriesPerTemplate, String feedName, VersaApi versaApi,
            MessageChannel rawFragmentMessageInputChannel, List<Template> templates, String dirId) {
        super();
        this.urlsCategoriesPerTemplate = urlsCategoriesPerTemplate;
        this.feedName = feedName;
        this.versaApi = versaApi;
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
        this.templates = templates;
        this.dirId = dirId;
    }

    @Override
    public Integer call() throws Exception {
        if (urlCategoryTemplateData(templates)) {
            return 1;
        }
        return 0;
    }

    public boolean urlCategoryTemplateData(List<Template> templates)  {
        Map<String, String> paramKeyValues = null;
        UrlCategoryResponse urlCategoryResponse = null;
        List<Map<String, String>> response = null;
        String reString = null;
        boolean isCompleted = false;
        for (Template template : templates) {
            paramKeyValues = new HashMap<>(2);
            response = new ArrayList<>();
            paramKeyValues.put(VersaIntegrationConstants.TEMPLATE_NAME, template.getName());
            paramKeyValues.put(VersaIntegrationConstants.ORG_NAME, template.getOrganization());
            try {
                reString = versaApi.versaGetCall(urlsCategoriesPerTemplate, paramKeyValues, String.class);
                LOG.info(reString);
            } catch (Exception e) {

                LOG.error(VersaIntegrationConstants.ERROR_WHILE_PULLING, UrlCategoryResponse.class.getName(),
                        VersaIntegrationConstants.URL_CATEGORY_RAW, e);
                isCompleted = true;
            }
            if (StringUtils.isNotBlank(reString)) {

                try {
                    urlCategoryResponse = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                            UrlCategoryResponse.class);
                } catch (Exception e) {
                    LOG.info(VersaIntegrationConstants.ERROR_WHILE_CONVERTNG,
                            VersaIntegrationConstants.URL_CATEGORY_RAW, e);
                    isCompleted = false;
                }
                if (urlCategoryResponse != null && !CollectionUtils.isEmpty(urlCategoryResponse.getUrlCategory())) {

                    getUrlCategories(urlCategoryResponse, template, response);
                    isCompleted = this.rawFragmentMessageInputChannel.send(
                            new GenericMessage<List<Map<String, String>>>(response, AvalonUtils.constructMessageHeader(
                                    VersaIntegrationConstants.URL_CATEGORY_RAW, dirId + "_" + feedName)));
                }
            } else {
                LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, urlsCategoriesPerTemplate);
                isCompleted = true;
            }
        }
        LOG.info("::::URL PER CATEGORY STATUS::::{}", isCompleted);
        return isCompleted;
    }

    private void getUrlCategories(UrlCategoryResponse urlCategoryResponse, Template template,
            List<Map<String, String>> response) {

        Map<String, String> eachRecord = null;
        for (UrlCategory org : urlCategoryResponse.getUrlCategory()) {
            eachRecord = new LinkedHashMap<>();
            eachRecord.put("uid", this.dirId + "_" + template.getOrganization() + "_" + template.getName() + "_"
                    + org.getCategoryName());
            eachRecord.put("url-category_category-name", org.getCategoryName());
            eachRecord.put("url-category_description", org.getDescription());
            eachRecord.put("organization", template.getOrganization());
            eachRecord.put("definitionType", "USERDEFINED");
            eachRecord.put("template-name", this.dirId + "_" + template.getOrganization() + "_" + template.getName());
            eachRecord.put("poolName", dirId);
            response.add(eachRecord);
        }
    }

}
