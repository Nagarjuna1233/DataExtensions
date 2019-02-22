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
import com.sap.tcl.avalon.versa.inbound.dtos.Application;
import com.sap.tcl.avalon.versa.inbound.dtos.PolicyGroupResponse;
import com.sap.tcl.avalon.versa.inbound.dtos.Rule;
import com.sap.tcl.avalon.versa.inbound.dtos.RulesPerPolicyResponse;
import com.sap.tcl.avalon.versa.inbound.dtos.SdwanPolicyGroup;
import com.sap.tcl.avalon.versa.inbound.dtos.Set;
import com.sap.tcl.avalon.versa.inbound.dtos.Template;
import com.sap.tcl.avalon.versa.inbound.dtos.UrlCategory;
import com.sap.tcl.avalon.versa.inbound.pojos.VersaQueryObject;

public class PolicyGroupDataCallable implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(PolicyGroupDataCallable.class);
    private String rulesPerPolicy;
    private String policyGroupUrl;
    private String feedName;
    private VersaApi versaApi;
    private MessageChannel rawFragmentMessageInputChannel;
    private List<Template> templates;
    private String dirId;

    public PolicyGroupDataCallable(String rulesPerPolicy, String policyGroupUrl, String feedName, VersaApi versaApi,
            MessageChannel rawFragmentMessageInputChannel, List<Template> templates, String dirId) {
        super();
        this.rulesPerPolicy = rulesPerPolicy;
        this.policyGroupUrl = policyGroupUrl;
        this.feedName = feedName;
        this.versaApi = versaApi;
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
        this.templates = templates;
        this.dirId = dirId;
    }

    @Override
    public Integer call() throws Exception {
        if (policyGroupData(templates)) {
            return 1;
        }
        return 0;
    }

    public boolean policyGroupData(List<Template> templates) {
        Map<String, String> paramKeyValues = null;
        PolicyGroupResponse aPPQOSPolicyResponse = null;
        List<Map<String, String>> response = null;
        List<VersaQueryObject> queries = new ArrayList<>();
        String reString = null;
        boolean isCompleted = false;
        for (Template template : templates) {
            paramKeyValues = new HashMap<>(2);
            paramKeyValues.put(VersaIntegrationConstants.TEMPLATE_NAME, template.getName());
            paramKeyValues.put(VersaIntegrationConstants.ORG_NAME, template.getOrganization());
            try {
                reString = versaApi.versaGetCall(policyGroupUrl, paramKeyValues, String.class);
                LOG.info(reString);
            } catch (Exception e) {
                LOG.error(VersaIntegrationConstants.ERROR_WHILE_PULLING, PolicyGroupResponse.class.getName(), e);
                isCompleted = true;
            }

            if (StringUtils.isNotBlank(reString)) {
                try {
                    aPPQOSPolicyResponse = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                            PolicyGroupResponse.class);
                    response = new ArrayList<>();
                    getPolicyGroup(response, queries, aPPQOSPolicyResponse, template);
                    isCompleted = this.rawFragmentMessageInputChannel.send(
                            new GenericMessage<List<Map<String, String>>>(response, AvalonUtils.constructMessageHeader(
                                    VersaIntegrationConstants.SDWAN_POLICY_GROUP_RAW, dirId + "_" + feedName)));
                } catch (Exception e) {
                    LOG.error(VersaIntegrationConstants.ERROR_WHILE_CONVERTNG, PolicyGroupResponse.class.getName(),
                            VersaIntegrationConstants.SDWAN_POLICY_GROUP_RAW, e);
                    isCompleted = false;
                }
            } else {
                LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, policyGroupUrl);
                isCompleted = true;
            }
        }
        LOG.info("{} Conversion status is {} ", VersaIntegrationConstants.SDWAN_POLICY_GROUP_RAW, isCompleted);
        rulesPerPolicyData(queries);
        return isCompleted;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public boolean rulesPerPolicyData(List<VersaQueryObject> queries) {
        Map<String, String> paramKeyValues = null;
        RulesPerPolicyResponse qosProfileRespose = null;
        List<Map<String, String>> response = null;

        String reString = null;
        boolean isCompleted = false;
        for (VersaQueryObject template : queries) {
            paramKeyValues = new HashMap<>(3);
            paramKeyValues.put(VersaIntegrationConstants.TEMPLATE_NAME, template.getTemplateName());
            paramKeyValues.put(VersaIntegrationConstants.ORG_NAME, template.getOrganizationName());
            paramKeyValues.put("policy-group-name", template.getPolicyGroupName());

            try {
                reString = versaApi.versaGetCall(rulesPerPolicy, paramKeyValues, String.class);
                LOG.info(reString);
            } catch (Exception e) {
                LOG.error(VersaIntegrationConstants.ERROR_WHILE_PULLING, RulesPerPolicyResponse.class.getName(), e);
                isCompleted = true;
            }
            if (StringUtils.isNotBlank(reString)) {
                try {
                    qosProfileRespose = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                            RulesPerPolicyResponse.class);
                    response = new ArrayList<>();
                    getPolicyRulesData(response, qosProfileRespose, template);
                    isCompleted = this.rawFragmentMessageInputChannel
                            .send(new GenericMessage<List<Map<String, String>>>(response,
                                    AvalonUtils.constructMessageHeader(VersaIntegrationConstants.RULES_PER_POLICY_RAW,
                                            dirId + "_" + VersaIntegrationConstants.RULES_PER_POLICY_FEED)));
                } catch (Exception e) {
                    LOG.info(VersaIntegrationConstants.ERROR_WHILE_CONVERTNG, RulesPerPolicyResponse.class.getName(),
                            VersaIntegrationConstants.RULES_PER_POLICY_RAW, e);
                    isCompleted = false;
                }
            } else {
                isCompleted = true;
                LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, rulesPerPolicy);
            }
        }
        return isCompleted;
    }

    protected VersaQueryObject getQueryObject(Template template, SdwanPolicyGroup group) {
        VersaQueryObject query = new VersaQueryObject();
        query.setTemplateName(template.getName());
        query.setOrganizationName(template.getOrganization());
        query.setPolicyGroupName(group.getName());
        return query;
    }

    private void getPolicyGroup(List<Map<String, String>> response, List<VersaQueryObject> queries,
            PolicyGroupResponse aPPQOSPolicyResponse, Template template) {
        Map<String, String> eachRecord = null;
        for (SdwanPolicyGroup group : aPPQOSPolicyResponse.getSdwanPolicyGroup()) {
            queries.add(getQueryObject(template, group));
            eachRecord = new LinkedHashMap<>();
            eachRecord.put("uid", this.dirId + "_" + template.getOrganization() + "_" + template.getName() + "_"
                    + group.getName() + VersaIntegrationConstants.SDWAN);
            eachRecord.put("sdwan-policy-group_name", group.getName());
            eachRecord.put("sdwan-policy-group_description", group.getDescription());
            eachRecord.put("template-name", this.dirId + "_" + template.getOrganization() + "_" + template.getName());
            eachRecord.put("poolName", dirId + "_POOL");
            eachRecord.put("organization", template.getOrganization());
            eachRecord.put("policyGroupType", "SDWAN");
            response.add(eachRecord);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void getPolicyRulesData(List<Map<String, String>> response, RulesPerPolicyResponse qosProfileRespose,
            VersaQueryObject template) {

        Map<String, String> eachRecord = null;
        Set set;
        Application application;
        UrlCategory urlCategory;

        for (Rule aPPQOSPolicy : qosProfileRespose.getRule()) {
            eachRecord = new HashMap<>();
            eachRecord.put(VersaIntegrationConstants.TEMPLATE_NAME,
                    this.dirId + "_" + template.getOrganizationName() + "_" + template.getTemplateName());
            eachRecord.put("uid",
                    this.dirId + "_" + template.getOrganizationName() + "_" + template.getTemplateName() + "_"
                            + template.getPolicyGroupName() + VersaIntegrationConstants.SDWAN + "_"
                            + aPPQOSPolicy.getName());
            eachRecord.put("policy-name", this.dirId + "_" + template.getOrganizationName() + "_"
                    + template.getTemplateName() + "_" + template.getPolicyGroupName() + "_SDWAN");
            eachRecord.put("rule_name", aPPQOSPolicy.getName());
            eachRecord.put("rule_description", aPPQOSPolicy.getDescription());
            eachRecord.put("rule_tag", AvalonUtils.listToString((Collection) aPPQOSPolicy.getTag(), ","));
            eachRecord.put("policyGroupType", "SDWAN");
            eachRecord.put("template", template.getTemplateName());
            eachRecord.put("organization", template.getOrganizationName());
            set = aPPQOSPolicy.getSet();
            if (set != null) {
                eachRecord.put("rule_set_action", set.getAction());
                eachRecord.put("rule_set_forwarding-profile",StringUtils.isNotEmpty(set.getForwardingProfile()) ? this.dirId + "_" + template.getOrganizationName() + "_"
                        + template.getTemplateName() + "_" + set.getForwardingProfile() : "");
            }else{
                eachRecord.put("rule_set_forwarding-profile", "");
            }

            if (aPPQOSPolicy.getMatch() != null) {
                application = aPPQOSPolicy.getMatch().getApplication();
                if (application != null) {
                    eachRecord.put("rule_match_application_predefined-application-list",
                            AvalonUtils.listToString((Collection) application.getPredefinedApplicationList(), ","));
                    eachRecord.put("rule_match_application_user-defined",
                            AvalonUtils.listToString((Collection) application.getUserDefinedApplicationList(), ","));

                }
                urlCategory = aPPQOSPolicy.getMatch().getUrlCategory();
                if (urlCategory != null) {
                    eachRecord.put("rule_match_url-category_user-defined",
                            AvalonUtils.listToString((Collection) urlCategory.getUserDefined(), ","));
                    eachRecord.put("rule_match_url-category_predefined",
                            AvalonUtils.listToString((Collection) urlCategory.getPredefined(), ","));
                }
            }
            eachRecord.put("poolName", dirId);
            response.add(eachRecord);
        }
    }
}
