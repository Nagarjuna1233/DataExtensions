package com.sap.tcl.avalon.versa.inbound.services;

import java.util.ArrayList;
import java.util.Collection;
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
import com.sap.tcl.avalon.versa.inbound.dtos.AppMatchRule;
import com.sap.tcl.avalon.versa.inbound.dtos.Template;
import com.sap.tcl.avalon.versa.inbound.dtos.UserDefinedApplication;
import com.sap.tcl.avalon.versa.inbound.dtos.UserDefinedApplicationDetailsResponse;
import com.sap.tcl.avalon.versa.inbound.dtos.UserDefinedApplicationResponse;
import com.sap.tcl.avalon.versa.inbound.pojos.VersaQueryObject;

public class UserDefinedAppsCallable implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(UserDefinedAppsCallable.class);

    private MessageChannel rawFragmentMessageInputChannel;
    private VersaApi versaApi;
    private String feedName;
    private List<Template> templates;

    private String userDefinedAppsUrl;
    private String userDefinedAppsDetails;
    private String dirId;

    public UserDefinedAppsCallable(MessageChannel rawFragmentMessageInputChannel, VersaApi versaApi, String feedName,
            List<Template> templates, String userDefinedAppsUrl, String userDefinedAppsDetails, String dirId) {
        super();
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
        this.versaApi = versaApi;
        this.feedName = feedName;
        this.templates = templates;
        this.userDefinedAppsUrl = userDefinedAppsUrl;
        this.userDefinedAppsDetails = userDefinedAppsDetails;
        this.dirId = dirId;
    }

    @Override
    public Integer call() throws Exception {
        if (userDefinedAppsData(templates)) {
            return 1;
        }
        return 0;
    }

    public boolean userDefinedAppsData(List<Template> templates) {
        Map<String, String> paramKeyValues = null;
        UserDefinedApplicationResponse userDefinedApplicationResponse = null;
        List<Map<String, String>> response = null;
        List<VersaQueryObject> queries = new ArrayList<>();
        String reString = null;
        boolean isCompleted = false;
        for (Template template : templates) {
            paramKeyValues = new HashMap<>(2);
            paramKeyValues.put(VersaIntegrationConstants.TEMPLATE_NAME, template.getName());
            paramKeyValues.put(VersaIntegrationConstants.ORG_NAME, template.getOrganization());

            try {
                reString = versaApi.versaGetCall(userDefinedAppsUrl, paramKeyValues, String.class);
                LOG.info(reString);
            } catch (Exception e) {

                LOG.error(VersaIntegrationConstants.ERROR_WHILE_PULLING, UserDefinedApplicationResponse.class.getName(),
                        e);
                isCompleted = true;
            }
            if (StringUtils.isNotBlank(reString)) {

                try {
                    userDefinedApplicationResponse = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                            UserDefinedApplicationResponse.class);
                    response = new ArrayList<>();
                    getUserDefinedApps(userDefinedApplicationResponse, template, queries, response);
                    isCompleted = this.rawFragmentMessageInputChannel.send(
                            new GenericMessage<List<Map<String, String>>>(response, AvalonUtils.constructMessageHeader(
                                    VersaIntegrationConstants.USER_DEFINED_APPS_RAW, dirId + "_" + feedName)));

                } catch (Exception e) {
                    LOG.error(VersaIntegrationConstants.ERROR_WHILE_CONVERTNG,
                            UserDefinedApplicationResponse.class.getName(),
                            VersaIntegrationConstants.USER_DEFINED_APPS_RAW, e);
                    isCompleted = false;
                }
            } else {
                isCompleted = true;
                LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, userDefinedAppsUrl);
            }
        }
        userDefinedAppsDetailsData(queries);
        return isCompleted;
    }

    public boolean userDefinedAppsDetailsData(List<VersaQueryObject> queries) {
        Map<String, String> paramKeyValues = null;
        UserDefinedApplicationDetailsResponse detailsResponse = null;
        List<Map<String, String>> response = null;
        String reString = null;
        boolean isCompleted = false;
        for (VersaQueryObject versaQueryObject : queries) {
            paramKeyValues = new HashMap<>(3);
            paramKeyValues.put(VersaIntegrationConstants.TEMPLATE_NAME, versaQueryObject.getTemplateName());
            paramKeyValues.put(VersaIntegrationConstants.ORG_NAME, versaQueryObject.getOrganizationName());
            paramKeyValues.put("user-defined-application-name", versaQueryObject.getUserDefinedAppName());

            try {
                reString = versaApi.versaGetCall(userDefinedAppsDetails, paramKeyValues, String.class);
                LOG.info(reString);
            } catch (Exception e) {
                LOG.error(VersaIntegrationConstants.ERROR_WHILE_PULLING,
                        UserDefinedApplicationDetailsResponse.class.getName(), e);
                isCompleted = true;
            }
            if (StringUtils.isNotBlank(reString)) {

                try {
                    detailsResponse = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                            UserDefinedApplicationDetailsResponse.class);
                    response = new ArrayList<>();
                    getUserDefinedAppDetails(detailsResponse, versaQueryObject, response);
                    isCompleted = this.rawFragmentMessageInputChannel
                            .send(new GenericMessage<List<Map<String, String>>>(response,
                                    AvalonUtils.constructMessageHeader(
                                            VersaIntegrationConstants.USER_DEFINED_APPS_DETAILS_RAW, dirId + "_"
                                                    + VersaIntegrationConstants.USER_DEFINED_APP_DETAILS_FEED)));
                } catch (Exception e) {
                    LOG.error(VersaIntegrationConstants.ERROR_WHILE_CONVERTNG,
                            UserDefinedApplicationDetailsResponse.class.getName(),
                            VersaIntegrationConstants.USER_DEFINED_APPS_RAW, e);
                    isCompleted = false;
                }
            } else {
                isCompleted = true;
                LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, userDefinedAppsDetails);
            }
        }
        return isCompleted;
    }

    protected VersaQueryObject getQueryObject(Template template, UserDefinedApplication group) {
        VersaQueryObject query = new VersaQueryObject();
        query.setTemplateName(template.getName());
        query.setOrganizationName(template.getOrganization());
        query.setUserDefinedAppName(group.getAppName());
        return query;
    }

    private void getUserDefinedApps(UserDefinedApplicationResponse userDefinedApplicationResponse, Template template,
            List<VersaQueryObject> queries, List<Map<String, String>> response) {

        Map<String, String> eachRecord = null;
        for (UserDefinedApplication group : userDefinedApplicationResponse.getUserDefinedApplication()) {
            queries.add(getQueryObject(template, group));
            eachRecord = new LinkedHashMap<>();
            eachRecord.put("uid", this.dirId + "_" + template.getOrganization() + "_" + template.getName() + "_"
                    + group.getAppName());
            eachRecord.put("user-defined-application_app-name", group.getAppName());
            eachRecord.put("user-defined-application_description", group.getDescription());
            eachRecord.put("user-defined-application_family", group.getFamily());
            eachRecord.put("user-defined-application_subfamily", group.getSubfamily());
            eachRecord.put("user-defined-application_productivity", group.getProductivity());
            eachRecord.put("user-defined-application_risk", group.getRisk());
            eachRecord.put("user-defined-application_tag", AvalonUtils.listToString((Collection) group.getTag(), ","));
            eachRecord.put("user-defined-application_precedence", group.getPrecedence());
            eachRecord.put("user-defined-application_app-timeout", group.getAppTimeout());
            eachRecord.put("template-name", this.dirId + "_" + template.getOrganization() + "_" + template.getName());
            eachRecord.put("organization", template.getOrganization());
            eachRecord.put("poolName", dirId);
            response.add(eachRecord);
        }

    }

    private void getUserDefinedAppDetails(UserDefinedApplicationDetailsResponse detailsResponse,
            VersaQueryObject versaQueryObject, List<Map<String, String>> response) {

        Map<String, String> eachRecord = null;
        for (AppMatchRule aPPQOSPolicy : detailsResponse.getAppMatchRules()) {
            eachRecord = new HashMap<>();
            eachRecord.put("uid",
                    this.dirId + "_" + versaQueryObject.getOrganizationName() + "_" + versaQueryObject.getTemplateName()
                            + "_" + versaQueryObject.getUserDefinedAppName() + "_" + aPPQOSPolicy.getRuleName());
            eachRecord.put("app-name", this.dirId + "_" + versaQueryObject.getOrganizationName() + "_"
                    + versaQueryObject.getTemplateName() + "_" + versaQueryObject.getUserDefinedAppName());
            eachRecord.put("app-match-rules_rule-name", aPPQOSPolicy.getRuleName());
            eachRecord.put("app-match-rules_host-pattern", aPPQOSPolicy.getHostPattern());
            eachRecord.put("app-match-rules_source-prefix", aPPQOSPolicy.getSourcePrefix());
            eachRecord.put("app-match-rules_destination-prefix", aPPQOSPolicy.getDestinationPrefix());
            if (null != aPPQOSPolicy.getSourcePort()) {
                eachRecord.put("app-match-rules_source-port_value", aPPQOSPolicy.getSourcePort().getValue());
            }
            if (null != aPPQOSPolicy.getDestinationPort()) {
                eachRecord.put("app-match-rules_destination-port_value", aPPQOSPolicy.getDestinationPort().getValue());
            }
            eachRecord.put("poolName", dirId + "_POOL");
            eachRecord.put("template-name", this.dirId + "_" + versaQueryObject.getOrganizationName() + "_"
                    + versaQueryObject.getTemplateName());
            response.add(eachRecord);
        }
    }
}
