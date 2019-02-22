package com.sap.tcl.avalon.versa.inbound.constants;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VersaIntegrationConstants {

    
    public static final String VERSA_DEFAULT_POOL_NAME = "GLOBAL_POOL";
    public static final String VERSA_DEFAULT_FEED_NAME = "DEFAULT_FEED";
    public static final String POOL = "_POOL";

    public static final String TEMPLATE_RAW = "RawVersaTemplates";
    public static final String URL_CATEGORY_RAW = "RawUrlCategory";
    public static final String DEVICE_GROUP_RAW = "RawVersaDeviceGroup";
    public static final String DEVICE_DETAILS_RAW = "RawVersaDeviceDetails";
    public static final String ORGAINZATION_RAW = "RawVersaOrganization";
    public static final String SDWAN_POLICY_GROUP_RAW = "RawVersaSDWANPolicyGroup";
    public static final String RULES_PER_POLICY_RAW = "RawVersaRulesPerPolicy";
    public static final String F_PROFILE_RAW = "RawVersaForwardingProfile";
    public static final String APPS_QOS_F_PROFILE_RAW = "RawVersaAppsQosForwardingProfile";
    public static final String SLA_PROFILE_RAW = "RawVersaSlaProfile";
    public static final String USER_DEFINED_APPS_DETAILS_RAW = "RawVersaUserDefinedAppDetails";
    public static final String USER_DEFINED_APPS_RAW = "RawVersaUserDefinedApps";
    public static final String DEVICE_GRP_TEMPLATE_RAW = "RawDeviceGrpandTempMap";
    public static final String APPS_QOS_POLICY_RAW = "RawAppQosPolicy";
    public static final String APPS_QOS_RULES_RAW = "RawAppQosRules";
    public static final String DIR_POLL_TIME = "RawDirectoryPollTime";
    public static final String APPS_RAW = "RawApplication";
    public static final String DEVICE_STATUS_RAW = "RawDeviceStatus";
    public static final String CIRCUITE_PRI = "RawVersaCircuitPriority";
    public static final String SDWAN_SYSTEM = "RawSdwanSystem";

    public static final String ORG_NAME = "organization-name";
    public static final String TEMPLATE_NAME = "template-name";

    public static final String CON_TEMPLATE = "CanonicalVersaTemplate";

    public static final ObjectMapper OBJ_MAPPER = new ObjectMapper();
    public static final String EMPTY_RES_MEG = "Skipping from conversion&messaging to Raw :resposne is empty,Url [{}]";

    public static final String TEMPLATES_FEED = "TEMPLATES_FEED";
    public static final String URL_CATEGORY_FEED = "URL_CATEGORY_FEED";
    public static final String DEVICE_GROUP_FEED = "DEVICE_GROUP_FEED";
    public static final String DEVICE_DETAILS_FEED = "DEVICE_DETAILS_FEED";
    public static final String ORGANIZATION_FEED = "ORGANIZATION_FEED";
    public static final String POLICY_GROUP_FEED = "POLICY_GROUP_FEED";
    public static final String RULES_PER_POLICY_FEED = "RULES_PER_POLICY_FEED";
    public static final String FORWARDING_PROFILE_FEED = "FORWARDING_PROFILE_FEED";
    public static final String APP_QOS_PROFILE_FEED = "APP_QOS_PROFILE_FEED";
    public static final String SLA_PROFILE_FEED = "SLA_PROFILE_FEED";
    public static final String USER_DEFINED_APP_DETAILS_FEED = "USER_DEFINED_APP_DETAILS_FEED";
    public static final String USER_DEFINED_APPS_FEED = "USER_DEFINED_APPS_FEED";
    public static final String DEVICE_GRP_TEMPMAP_FEED = "DEVICE_GRP_TEMPMAP_FEED";
    public static final String APP_QOS_POLICY_FEED = "APP_QOS_POLICY_FEED";
    public static final String APPQOS_RULE_FEED = "APPQOS_RULE_FEED";
    public static final String DIRECTORY_POLL_TIME_FEED = "DIRECTORY_POLL_TIME_FEED";
    public static final String PRE_APPLICATION_FEED = "PRE_APPLICATION_FEED";
    public static final String DEVICE_STATUS_FEED = "DEVICE_STATUS_FEED";
    public static final String CIRCUT_PRIORITY_FEED = "CIRCUT_PRIORITY_FEED";

    public static final String SDWAN = "_SDWAN";
    public static final String QOS = "_QOS";
    public static final String ERROR_WHILE_CONVERTNG = "Error While Converting {} json to {},error is {}";
    public static final String ERROR_WHILE_PULLING = "Error While Pulling {} data,error is {}";
    
    
    private VersaIntegrationConstants() {

    }

}
