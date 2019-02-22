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
import com.sap.tcl.avalon.versa.inbound.dtos.APPQOSPolicy;
import com.sap.tcl.avalon.versa.inbound.dtos.APPQOSPolicyGroup;
import com.sap.tcl.avalon.versa.inbound.dtos.APPQOSPolicyResponse;
import com.sap.tcl.avalon.versa.inbound.dtos.APPQOSRulesApplication;
import com.sap.tcl.avalon.versa.inbound.dtos.APPQOSRulesMatch;
import com.sap.tcl.avalon.versa.inbound.dtos.APPQOSRulesResponse;
import com.sap.tcl.avalon.versa.inbound.dtos.APPQOSRulesSet;
import com.sap.tcl.avalon.versa.inbound.dtos.APPQOSRulesUrlCategory;
import com.sap.tcl.avalon.versa.inbound.dtos.Template;
import com.sap.tcl.avalon.versa.inbound.pojos.VersaQueryObject;

public class AppsQosPolicyCallable implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(AppsQosPolicyCallable.class);
    private String appsQosRulesUrl;
    private String feedName;
    private VersaApi versaApi;
    private MessageChannel rawFragmentMessageInputChannel;
    List<Template> templates;
    String dirId;

    private String qosPolicyUrl;

    public AppsQosPolicyCallable(String qosPolicyUrl, String appsQosRulesUrl, String feedName, VersaApi versaApi,
            MessageChannel rawFragmentMessageInputChannel, List<Template> templates, String dirId) {
        super();
        this.qosPolicyUrl = qosPolicyUrl;
        this.appsQosRulesUrl = appsQosRulesUrl;
        this.feedName = feedName;
        this.versaApi = versaApi;
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
        this.templates = templates;
        this.dirId = dirId;
    }

    @Override
    public Integer call() throws Exception {
        if (appsQosPolicyData(templates)) {
            return 1;
        }
        return 0;
    }

    /**
     * Method to get Application Qos Policies
     * 
     * @param templates
     * @return isCompleted
     */
    public boolean appsQosPolicyData(List<Template> templates) {
        Map<String, String> paramKeyValues = null;
        APPQOSPolicyResponse aPPQOSPolicyResponse = null;
        List<Map<String, String>> response = null;
        List<VersaQueryObject> queries = new ArrayList<>();
        String reString = null;
        boolean isCompleted = false;
        for (Template template : templates) {
            paramKeyValues = new HashMap<>();
            paramKeyValues.put(VersaIntegrationConstants.TEMPLATE_NAME, template.getName());
            paramKeyValues.put(VersaIntegrationConstants.ORG_NAME, template.getOrganization());
            try {
                reString = versaApi.versaGetCall(qosPolicyUrl, paramKeyValues, String.class);
                LOG.info(reString);
            } catch (Exception ex) {
                LOG.error(VersaIntegrationConstants.ERROR_WHILE_PULLING, APPQOSPolicyResponse.class.getName(), ex);
                isCompleted = true;
            }
            if (StringUtils.isNotBlank(reString)) {

                try {
                    aPPQOSPolicyResponse = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                            APPQOSPolicyResponse.class);
                    response = new ArrayList<>();
                    getAppQosPolicies(aPPQOSPolicyResponse, template, queries, response);
                    isCompleted = this.rawFragmentMessageInputChannel.send(
                            new GenericMessage<List<Map<String, String>>>(response, AvalonUtils.constructMessageHeader(
                                    VersaIntegrationConstants.APPS_QOS_POLICY_RAW, dirId + "_" + feedName)));
                } catch (Exception e) {
                    LOG.error(VersaIntegrationConstants.ERROR_WHILE_CONVERTNG, APPQOSPolicyResponse.class.getName(),
                            VersaIntegrationConstants.APPS_QOS_POLICY_RAW, e);
                    isCompleted = false;
                }
            } else {
                LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, qosPolicyUrl);
                isCompleted = true;
            }
        }
        appsQosRulesData(queries);
        return isCompleted;
    }

    /**
     * 
     * @param queries
     * @return
     * @throws Exception
     */
    public boolean appsQosRulesData(List<VersaQueryObject> queries) {
        Map<String, String> paramKeyValues = null;
        APPQOSRulesResponse qosProfileRespose = null;
        List<Map<String, String>> response = null;

        boolean isCompleted = false;
        String reString = null;
        for (VersaQueryObject template : queries) {
            paramKeyValues = new HashMap<>(3);
            paramKeyValues.put(VersaIntegrationConstants.TEMPLATE_NAME, template.getTemplateName());
            paramKeyValues.put(VersaIntegrationConstants.ORG_NAME, template.getOrganizationName());
            paramKeyValues.put("qos-policy-name", template.getPolicyGroupName());
            try {
                reString = versaApi.versaGetCall(appsQosRulesUrl, paramKeyValues, String.class);
                LOG.info(reString);
            } catch (Exception e) {
                LOG.error(VersaIntegrationConstants.ERROR_WHILE_PULLING, APPQOSRulesResponse.class.getName(), e);
                isCompleted = true;
            }
            if (StringUtils.isNotBlank(reString)) {
                try {
                    qosProfileRespose = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                            APPQOSRulesResponse.class);
                    response = new ArrayList<>();
                    getFinalResponse(qosProfileRespose, response, template);
                    isCompleted = this.rawFragmentMessageInputChannel
                            .send(new GenericMessage<List<Map<String, String>>>(response,
                                    AvalonUtils.constructMessageHeader(VersaIntegrationConstants.APPS_QOS_RULES_RAW,
                                            dirId + "_" + VersaIntegrationConstants.APPQOS_RULE_FEED)));
                } catch (Exception e) {
                    LOG.info(VersaIntegrationConstants.ERROR_WHILE_CONVERTNG, APPQOSRulesResponse.class.getName(),
                            VersaIntegrationConstants.APPS_QOS_RULES_RAW, e);
                    isCompleted = false;
                }
            } else {
                LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, appsQosRulesUrl);
                isCompleted = true;
            }
        }
        return isCompleted;
    }

    /**
     * 
     * @param template
     * @param group
     * @return
     */
    private static VersaQueryObject getQueryObject(Template template, APPQOSPolicyGroup group) {
        VersaQueryObject query = new VersaQueryObject();
        query.setTemplateName(template.getName());
        query.setOrganizationName(template.getOrganization());
        query.setPolicyGroupName(group.getName());
        return query;
    }

    /**
     * 
     * @param qosProfileRespose
     * @param response
     * @param template
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void getFinalResponse(APPQOSRulesResponse qosProfileRespose, List<Map<String, String>> response,
            VersaQueryObject template) {

        Map<String, String> eachRecord = null;
        APPQOSRulesSet aPPQOSRulesSet;
        APPQOSRulesApplication aPPQOSRulesApplication;
        APPQOSRulesUrlCategory aPPQOSRulesUrlCategory;
        APPQOSRulesMatch aPPQOSRulesMatch;

        for (APPQOSPolicy aPPQOSPolicy : qosProfileRespose.getAppQosPolicy()) {
            eachRecord = new HashMap<>();
            eachRecord.put(VersaIntegrationConstants.TEMPLATE_NAME,
                    this.dirId + "_" + template.getOrganizationName() + "_" + template.getTemplateName());

            eachRecord.put("uid", this.dirId + "_" + template.getOrganizationName() + "_" + template.getTemplateName()
                    + "_" + template.getPolicyGroupName() + "_QOS" + "_" + aPPQOSPolicy.getName());
            eachRecord.put("qos-policy-group-name", this.dirId + "_" + template.getOrganizationName() + "_"
                    + template.getTemplateName() + "_" + template.getPolicyGroupName() + "_QOS");

            eachRecord.put("app-qos-policy_name", aPPQOSPolicy.getName());
            eachRecord.put("app-qos-policy_description", aPPQOSPolicy.getDescription());
            eachRecord.put("poolName", this.dirId);
            eachRecord.put("policyGroupType", "QOS");
            eachRecord.put("template", template.getTemplateName());
            eachRecord.put("organization", template.getOrganizationName());
            aPPQOSRulesSet = aPPQOSPolicy.getSet();
            if (aPPQOSRulesSet != null) {
                eachRecord.put("app-qos-policy_set_qos-profile",StringUtils.isNotEmpty(aPPQOSRulesSet.getQosProfile()) ? this.dirId + "_" + template.getOrganizationName() + "_"
                        + template.getTemplateName() + "_" + aPPQOSRulesSet.getQosProfile() : "");
            }else{
                eachRecord.put("app-qos-policy_set_qos-profile", "");
            }

            aPPQOSRulesMatch = aPPQOSPolicy.getMatch();
            if (null != aPPQOSRulesMatch) {
                aPPQOSRulesApplication = aPPQOSRulesMatch.getApplication();
                if (null != aPPQOSRulesApplication) {
                    eachRecord.put("app-qos-policy_application_predefined-application-list", AvalonUtils
                            .listToString((Collection) aPPQOSRulesApplication.getPredefinedApplicationList(), ","));
                    eachRecord.put("app-qos-policy_application_user-defined-application-list", AvalonUtils
                            .listToString((Collection) aPPQOSRulesApplication.getUserDefinedApplicationList(), ","));
                }
                aPPQOSRulesUrlCategory = aPPQOSRulesMatch.getUrlCategory();
                if (null != aPPQOSRulesUrlCategory) {
                    eachRecord.put("app-qos-policy_url-category_predefined",
                            AvalonUtils.listToString((Collection) aPPQOSRulesUrlCategory.getPredefined(), ","));
                    eachRecord.put("app-qos-policy_url-category_user-defined",
                            AvalonUtils.listToString((Collection) aPPQOSRulesUrlCategory.getUserDefined(), ","));
                }
            }
            response.add(eachRecord);
        }
    }

    private void getAppQosPolicies(APPQOSPolicyResponse aPPQOSPolicyResponse, Template template,
            List<VersaQueryObject> queries, List<Map<String, String>> response) {

        Map<String, String> eachRecord = null;
        for (APPQOSPolicyGroup group : aPPQOSPolicyResponse.getAppQosPolicyGroup()) {
            queries.add(getQueryObject(template, group));
            eachRecord = new LinkedHashMap<>();

            eachRecord.put("uid", this.dirId + "_" + template.getOrganization() + "_" + template.getName() + "_"
                    + group.getName() + "_QOS");
            eachRecord.put("app-qos-policy-group_name", group.getName());
            eachRecord.put("app-qos-policy-group_description", group.getDescription());
            eachRecord.put("template-name", this.dirId + "_" + template.getOrganization() + "_" + template.getName());
            eachRecord.put("poolName", this.dirId + "_POOL");
            eachRecord.put("organization", this.dirId + "_" + template.getOrganization() + "_" + template.getName());
            eachRecord.put("policyGroupType", "QOS");
            response.add(eachRecord);
        }

    }

}
