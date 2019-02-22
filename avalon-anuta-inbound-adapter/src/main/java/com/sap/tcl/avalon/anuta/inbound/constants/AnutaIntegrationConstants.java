package com.sap.tcl.avalon.anuta.inbound.constants;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AnutaIntegrationConstants {

    public static final String ERROR_WHILE_PULLING = "Error while Pulling data using the api url {},cause is {}";
    public static final String EMPTY_RES_MEG = "Skipping from conversion&messaging to Raw :resposne is empty,Url [{}]";
    public static final String ERROR_WHILE_COVERTING = "Error While Converting {} Json to Object type {},Cause is {} ";
    
    public static final String ALL_DEVICE_CHANNEL_HEAD = "ALL-DEVICE";
    public static final String ALL_SITE_CHANNEL_HEAD = "ALL-SITE";
    public static final String APPS_CHANNEL_HEAD = "APPS";
    public static final String CUSTOM_APPS_CHANNEL_HEAD = "CUSTOM-APPS";
    public static final String DEVICE_CLS_MAP_CHANNEL_HEAD = "DEVICE-CLS-MAP";

    public static final String POOL = "_POOL";
    public static final String POOL_NAME = "poolName";

    public static final String ANUTA_DEFAULT_POOL_NAME = "GLOBAL_POOL";
    public static final String ANUTA_DEFAULT_FEED_NAME = "DEFAULT_FEED";

    public static final String RAW_ALLDEVICES = "RawAllDevices";
    public static final String RAW_DEVICE_ROUTE = "RawDeviceRoute";
    public static final String RAW_APPS = "RawApplications";
    public static final String RAW_CUSTOM_APPS = "RawCustomApplications";
    public static final String RAW_PROTOCOL = "RawProtocol";
    public static final String RAW_APP_CONDITION = "RawAppCondition";
    public static final String RAW_CUSTOM_APPS_RULES = "RawCustomApplicationRules";
    public static final String RAW_DEVICE_C_MAP = "RawDeviceClassMap";
    public static final String RAW_ALL_SITES = "RawSiteServices";
    public static final String SDWAN_SYSTEM = "RawSdwanSystem";
    public static final String DIR_POLL_TIME = "RawDirectoryPollTime";

    public static final String RAW_QOS_POLICY = "RawQosPolicy";

    public static final String DEVICE_FEED = "DEVICE_FEED";
    public static final String SITES_FEED = "SITES_FEED";
    public static final String CUSTOM_APPS_FEED = "CUSTOM_APPS_FEED";
    public static final String RAW_APPS_FEED = "RAW_APPS_FEED";
    public static final String DEVICE_CLASS_MAP_FEED = "DEVICE_CLASS_MAP_FEED";

    public static final ObjectMapper OBJ_MAPPER = new ObjectMapper();
    public static final String APP_NAME = "app-name";
    public static final String UNIQUEID = "uniqueId";
    public static final String DIRID = "dirId";
    public static final String ORGNAME = "organization-name";

    private AnutaIntegrationConstants() {

    }

}
