package com.sap.tcl.avalon.anuta.inbound.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hybris.datahub.api.event.InitiateCompositionEvent;
import com.hybris.datahub.service.EventPublicationService;
import com.sap.tcl.avalon.anuta.inbound.constants.AnutaIntegrationConstants;
import com.sap.tcl.avalon.anuta.inbound.conversion.utils.AnutaConversionUtill;
import com.sap.tcl.avalon.anuta.inbound.dtos.AccessList;
import com.sap.tcl.avalon.anuta.inbound.dtos.AccessListRule;
import com.sap.tcl.avalon.anuta.inbound.dtos.AccessLists;
import com.sap.tcl.avalon.anuta.inbound.dtos.AllSitesResponse;
import com.sap.tcl.avalon.anuta.inbound.dtos.ControllerDevicesResponse;
import com.sap.tcl.avalon.anuta.inbound.dtos.Customer;
import com.sap.tcl.avalon.anuta.inbound.dtos.DCMControllerDevicesResponse;
import com.sap.tcl.avalon.anuta.inbound.dtos.DCMDevice;
import com.sap.tcl.avalon.anuta.inbound.dtos.L3featuresRoutes;
import com.sap.tcl.avalon.anuta.inbound.dtos.PredefinedApplications;
import com.sap.tcl.avalon.utils.AvalonUtils;

public class AnutaPullServiceWorker {

    private AnutaApi anutaApi;

    private AnutaApiService anutaApiService;

    private MessageChannel rawFragmentMessageInputChannel;

    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

    private CreateFeedPoolService createFeedPoolService;

    private String allDevicesUrl;
    private String primeAppsUrl;
    private String allSitesUrl;
    private String customAppsUrl;
    private String deviceClassMapUrl;
    private String pollTime;
    private String directorId;
    private String userName;
    private String password;
    private String baseUrl;
    private EventPublicationService eventPublicationService;

    private static final Logger LOG = LoggerFactory.getLogger(AnutaPullServiceWorker.class);

    public void pull() {
        LOG.info("Pulling data .............");
        try {
            getCreateFeedPoolService().checkFeedsAndPools(getDirectorId());
            pullData();
            getEventPublicationService().publishEvent(new InitiateCompositionEvent(
                    getCreateFeedPoolService().getPoolIdbyName(getDirectorId() + AnutaIntegrationConstants.POOL)));
        } catch (Exception e) {
            LOG.error("{} While Pulling data", e);
        }
    }

    @Async
    public void pullData()  {
        pollTime = String.valueOf(System.currentTimeMillis());
        Set<String> organizations = getAllDevicesData();

        pushPollStartTime(getDirectorId(), pollTime, organizations);
        Map<String, String> preDefinedapplications = primeAppsData();
        getCustomAppsData();
        getAllSitesData();
        deviceClassMapData(preDefinedapplications);
        LOG.info("Pulling data completed..");
    }

    @SuppressWarnings({ "unchecked" })
    public Set<String> getAllDevicesData() {
        Set<String> organizations = new HashSet<>();
        String reString = null;
        try {
            reString = anutaApi.anutaGetCall(allDevicesUrl, new HashMap<String, String>(), String.class);
        } catch (Exception e) {
            LOG.error(AnutaIntegrationConstants.ERROR_WHILE_PULLING,
                    AvalonUtils.buildPathParamUrl(baseUrl + allDevicesUrl, null),e);
        }
        if (StringUtils.isNotBlank(reString)) {
            LOG.info(":::::::::::::::::::::Loading Devices Data Started:::::::::::::::::::::");
            ControllerDevicesResponse controllerDevicesResponse = null;
            try {
                controllerDevicesResponse = OBJ_MAPPER.readValue(reString, ControllerDevicesResponse.class);
            } catch (IOException e) {
              LOG.error(AnutaIntegrationConstants.ERROR_WHILE_COVERTING,ControllerDevicesResponse.class.getName(),ControllerDevicesResponse.class.getName(),e);
            }
            Object[] allRoutes = anutaApiService.getControllerDevicesInterFaceRoutes(controllerDevicesResponse);
            Map<String, L3featuresRoutes> routes = allRoutes.length > 0 ? (Map<String, L3featuresRoutes>) allRoutes[0]
                    : new HashMap<>();
            Map<String, String> interfaces = allRoutes.length > 0 ? (Map<String, String>) allRoutes[1] : null;
            ControllerDevicesResponse updatedControllerDevicesResponse = allRoutes.length > 0
                    ? (ControllerDevicesResponse) allRoutes[2] : null;
            List<Map<String, String>> result = new ArrayList<>();
            Map<String, String> deviceIds = new HashMap<>();
            AnutaConversionUtill.getAllDevices(updatedControllerDevicesResponse, getDirectorId(), organizations,
                    deviceIds, interfaces, result);
            this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(result,
                    AvalonUtils.constructMessageHeader(AnutaIntegrationConstants.RAW_ALLDEVICES,
                            getDirectorId() + "_" + AnutaIntegrationConstants.DEVICE_FEED)));
            LOG.info(":::::::::::::::::::::Device Data Loaded Successfully:::::::::::::::::::::");
            result = new ArrayList<>();
            AnutaConversionUtill.getDeviceRoutes(routes, deviceIds, result, getDirectorId());
            this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(result,
                    AvalonUtils.constructMessageHeader(AnutaIntegrationConstants.RAW_DEVICE_ROUTE,
                            getDirectorId() + "_" + AnutaIntegrationConstants.DEVICE_FEED)));
            LOG.info(":::::::::::::::::::::Device Routed Data Loaded Successfully:::::::::::::::::::::");
        }

        return organizations;
    }

    public void getAllSitesData() {
        String reString = null;
        try {
            reString = anutaApi.anutaGetCall(allSitesUrl, new HashMap<String, String>(), String.class);
        } catch (Exception e) {
            LOG.error(AnutaIntegrationConstants.ERROR_WHILE_PULLING,
                    AvalonUtils.buildPathParamUrl(baseUrl + allSitesUrl, null),e);
        }

        if (StringUtils.isNotBlank(reString)) {
            LOG.info(":::::::::::::::::::::Loading Sites Data Started:::::::::::::::::::::");
            AllSitesResponse controllerDevicesResponse = null;
            try {
                controllerDevicesResponse = OBJ_MAPPER.readValue(reString, AllSitesResponse.class);
            } catch (IOException e) {
               LOG.error(AnutaIntegrationConstants.ERROR_WHILE_COVERTING,AllSitesResponse.class.getName(),AllSitesResponse.class.getName(),e);
            }
            List<Map<String, String>> response = new ArrayList<>();
            for (Customer customer :controllerDevicesResponse!=null ? controllerDevicesResponse.getCpedeploymentManagedCpeServices().getCustomer() : new ArrayList<Customer>() ) {
                if (!StringUtils.isEmpty(customer.getName())) {
                    AnutaConversionUtill.mapAllSitesData(customer, response, getDirectorId());
                }
            }
            this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(response,
                    AvalonUtils.constructMessageHeader(AnutaIntegrationConstants.RAW_ALL_SITES,
                            getDirectorId() + "_" + AnutaIntegrationConstants.SITES_FEED)));
            LOG.info(":::::::::::::::::::::Managed Cpe Services  Data Loaded Successfully:::::::::::::::::::::");
        } else {
            LOG.info(":::::::::::::::::::::Error While Converting Managed Cpe Services Data:::::::::::::::::::::");
        }
    }

    public Map<String, String> primeAppsData()  {

        Map<String, String> applicationMap = new HashMap<>();
        String reString = null;
        try {
            reString = anutaApi.anutaGetCall(primeAppsUrl, new HashMap<String, String>(), String.class);
        } catch (Exception e) {
            LOG.error(AnutaIntegrationConstants.ERROR_WHILE_PULLING,
                    AvalonUtils.buildPathParamUrl(baseUrl + primeAppsUrl, null),e);
        }
        if (StringUtils.isNotBlank(reString)) {

            LOG.info(":::::::::::::::::::::Loading Predefined Application Data:::::::::::::::::::::");
            List<Map<String, String>> response = null;
            PredefinedApplications appsCpedeploymentClassMap = null;
            try {
                appsCpedeploymentClassMap = OBJ_MAPPER.readValue(reString, PredefinedApplications.class);
            } catch (Exception e) {
               LOG.error(AnutaIntegrationConstants.ERROR_WHILE_COVERTING,PredefinedApplications.class.getName(),PredefinedApplications.class.getName(),e);
               
            } 
            if (appsCpedeploymentClassMap != null
                    && !CollectionUtils.isEmpty(appsCpedeploymentClassMap.getCpedeploymentClassMap().getProtocol())) {
                response = new ArrayList<>();
                for (String app : appsCpedeploymentClassMap.getCpedeploymentClassMap().getProtocol()) {
                    Map<String, String> predefinedApps = new HashMap<>();
                    predefinedApps.put(AnutaIntegrationConstants.APP_NAME, getDirectorId() + "_" + app);
                    predefinedApps.put("name", app);
                    applicationMap.put(app, app);
                    predefinedApps.put(AnutaIntegrationConstants.ORGNAME, "");
                    predefinedApps.put(AnutaIntegrationConstants.DIRID, getDirectorId());
                    predefinedApps.put("description",
                            appsCpedeploymentClassMap.getCpedeploymentClassMap().getDescription());
                    predefinedApps.put(AnutaIntegrationConstants.POOL_NAME,
                            getDirectorId() + AnutaIntegrationConstants.POOL);
                    response.add(predefinedApps);
                }
                this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(response,
                        AvalonUtils.constructMessageHeader(AnutaIntegrationConstants.RAW_APPS,
                                getDirectorId() + "_" + AnutaIntegrationConstants.RAW_APPS_FEED)));
                LOG.info(":::::::::::::::::::::Predefined Applications Data Loaded Successfully:::::::::::::::::::::");
            }
        } else {
            LOG.info(":::::::::::::::::::::Error While Converting Predefined Application Data:::::::::::::::::::::");
        }
        return applicationMap;
    }

    public void getCustomAppsData()  {
        String reString = null;
        try {
            reString = anutaApi.anutaGetCall(customAppsUrl, new HashMap<String, String>(), String.class);
        } catch (Exception e) {
            LOG.error(AnutaIntegrationConstants.ERROR_WHILE_PULLING,
                    AvalonUtils.buildPathParamUrl(baseUrl + customAppsUrl, null),e);
        }
        if (StringUtils.isNotBlank(reString)) {
            LOG.info("::::::::::::::::::::Loading Customo Applications Data Started:::::::::::::::::::::");
            AllSitesResponse controllerDevicesResponse = null;
            try {
                controllerDevicesResponse = OBJ_MAPPER.readValue(reString, AllSitesResponse.class);
            } catch (IOException e) {
               LOG.error(AnutaIntegrationConstants.ERROR_WHILE_COVERTING,AllSitesResponse.class,AllSitesResponse.class,e);
            }
            List<Map<String, String>> devicesResult = new ArrayList<>();
            List<Map<String, String>> rulesResult = new ArrayList<>();
            List<Map<String, String>> protocolEnumData = new ArrayList<>();
            List<Map<String, String>> appConditionEnumData = new ArrayList<>();
            Map<String, String> deviceMap = null;
            AccessLists accLists = null;

            for (Customer customer : controllerDevicesResponse.getCpedeploymentManagedCpeServices().getCustomer()) {
                accLists = customer.getAccessLists();
                    for (AccessList accList : (!StringUtils.isEmpty(customer.getName()) && null != accLists) ? accLists.getAccessList() : new ArrayList<AccessList>()) {
                        deviceMap = new LinkedHashMap<>();
                        deviceMap.put("organization-name", customer.getName());
                        deviceMap.put("app-name", accList.getName());
                        deviceMap.put(AnutaIntegrationConstants.POOL_NAME,
                                getDirectorId() + AnutaIntegrationConstants.POOL);
                        deviceMap.put(AnutaIntegrationConstants.UNIQUEID,
                                getDirectorId() + "_" + customer.getName() + "_" + accList.getName());
                        deviceMap.put(AnutaIntegrationConstants.DIRID, getDirectorId());
                        devicesResult.add(deviceMap);

                            getApplicationRules(accList, customer, rulesResult, protocolEnumData, appConditionEnumData);
                    }
            }
            this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(devicesResult,
                    AvalonUtils.constructMessageHeader(AnutaIntegrationConstants.RAW_CUSTOM_APPS,
                            getDirectorId() + "_" + AnutaIntegrationConstants.CUSTOM_APPS_FEED)));
            LOG.info(":::::::::::::::::::::Custom Applications Data Loaded Successfully:::::::::::::::::::::");

            this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(rulesResult,
                    AvalonUtils.constructMessageHeader(AnutaIntegrationConstants.RAW_CUSTOM_APPS_RULES,
                            getDirectorId() + "_" + AnutaIntegrationConstants.CUSTOM_APPS_FEED)));
            LOG.info(":::::::::::::::::::::Application Rules Data Loaded Successfully:::::::::::::::::::::");

            this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(protocolEnumData,
                    AvalonUtils.constructMessageHeader(AnutaIntegrationConstants.RAW_PROTOCOL,
                            getDirectorId() + "_" + AnutaIntegrationConstants.CUSTOM_APPS_FEED)));

            this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(appConditionEnumData,
                    AvalonUtils.constructMessageHeader(AnutaIntegrationConstants.RAW_APP_CONDITION,
                            getDirectorId() + "_" + AnutaIntegrationConstants.CUSTOM_APPS_FEED)));
        } else {
            LOG.info(":::::::::::::::::::::Error While Converting Custom Applications Data:::::::::::::::::::::");
        }
    }

    public void deviceClassMapData(Map<String, String> preDefinedapplications)  {
        String reString = null;
        try {
            reString = anutaApi.anutaGetCall(deviceClassMapUrl, new HashMap<String, String>(), String.class);
        } catch (Exception e) {
            LOG.error(AnutaIntegrationConstants.ERROR_WHILE_PULLING,
                    AvalonUtils.buildPathParamUrl(baseUrl + deviceClassMapUrl, null),e);
        }
        if (StringUtils.isNotBlank(reString)) {
            LOG.info(":::::::::::::::::::::Loading Policies Data Started:::::::::::::::::::::");
            List<Map<String, String>> response = new ArrayList<>();
            List<Map<String, String>> qosPolicies = new ArrayList<>();
            DCMControllerDevicesResponse controllerDevicesResponse = null;

            try {
                controllerDevicesResponse = OBJ_MAPPER.readValue(reString, DCMControllerDevicesResponse.class);
            } catch (IOException e) {
               LOG.error(AnutaIntegrationConstants.ERROR_WHILE_COVERTING,DCMControllerDevicesResponse.class,DCMControllerDevicesResponse.class,e);
            }
            for (DCMDevice dcmDevice : controllerDevicesResponse.getControllerDevices().getDevice()) {

                String owner = dcmDevice.getDeviceOwner() != null ? dcmDevice.getDeviceOwner().getOwner() : "";
                if (!StringUtils.isEmpty(owner) && null != dcmDevice.getQosClassMaps()
                        && !CollectionUtils.isEmpty(dcmDevice.getQosClassMaps().getClassMap())) {
                    AnutaConversionUtill.getDeviceQosPolicies(dcmDevice, owner, preDefinedapplications, response,
                            qosPolicies, getDirectorId());
                }
            }
            this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(response,
                    AvalonUtils.constructMessageHeader(AnutaIntegrationConstants.RAW_DEVICE_C_MAP,
                            getDirectorId() + "_" + AnutaIntegrationConstants.DEVICE_CLASS_MAP_FEED)));
            LOG.info(":::::::::::::::::::::RawDeviceClassMap Data Loaded Successfully:::::::::::::::::::::");

            this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(qosPolicies,
                    AvalonUtils.constructMessageHeader(AnutaIntegrationConstants.RAW_QOS_POLICY,
                            getDirectorId() + "_" + AnutaIntegrationConstants.DEVICE_CLASS_MAP_FEED)));
            LOG.info(":::::::::::::::::::::Policies Data Loaded Successfully:::::::::::::::::::::");
        } else {
            LOG.info(":::::::::::::::::::::Error While Converting Policies Data:::::::::::::::::::::");
        }
    }

    public void pushPollStartTime(String dirName, String poolTime, Set<String> organizations) {

        List<Map<String, String>> dirTimes = new ArrayList<>();
        Map<String, String> mapTime = new HashMap<>();
        mapTime.put("dirName", dirName);
        mapTime.put("pollStartTime", poolTime);
        mapTime.put("poolName", getDirectorId() + AnutaIntegrationConstants.POOL);
        dirTimes.add(mapTime);
        this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(dirTimes,
                AvalonUtils.constructMessageHeader(AnutaIntegrationConstants.DIR_POLL_TIME,
                        getDirectorId() + "_" + AnutaIntegrationConstants.DEVICE_FEED)));

        List<Map<String, String>> sdwanSystems = new ArrayList<>();

        for (String organization : organizations) {
            Map<String, String> sdwansystem = new HashMap<>();
            sdwansystem.put(AnutaIntegrationConstants.DIRID, dirName);
            sdwansystem.put("dirName", dirName.split("_")[2]);
            sdwansystem.put(AnutaIntegrationConstants.POOL_NAME, getDirectorId() + AnutaIntegrationConstants.POOL);
            sdwansystem.put("organization", organization);
            sdwansystem.put("userName", getUserName());
            sdwansystem.put("password", getPassword());
            sdwansystem.put("base-url", getBaseUrl());
            sdwanSystems.add(sdwansystem);
        }
        this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(sdwanSystems,
                AvalonUtils.constructMessageHeader(AnutaIntegrationConstants.SDWAN_SYSTEM,
                        getDirectorId() + "_" + AnutaIntegrationConstants.DEVICE_FEED)));
        LOG.info(":::::::::::::::::::::SDWAN SYSTEM data Pushed Successfully:::::::::::::::::::::");
    }

    private void getApplicationRules(AccessList accList, Customer customer, List<Map<String, String>> rulesResult,
            List<Map<String, String>> protocolEnumData, List<Map<String, String>> appConditionEnumData) {

        Map<String, String> rulesMap = null;
        List<AccessListRule> rulesList =(accList!=null && !CollectionUtils.isEmpty( accList.getAccessListRules())) ? accList.getAccessListRules() : new ArrayList<AccessListRule>();
        int size = rulesList.size();
        for (int i = 0; i < size; i++) {
            rulesMap = new LinkedHashMap<>();
            Map<String, String> protocolMap = new HashMap<>();
            Map<String, String> appConditionMap = new HashMap<>();
            AccessListRule accListRule = rulesList.get(i);
            rulesMap.put(AnutaIntegrationConstants.DIRID, getDirectorId());
            rulesMap.put(AnutaIntegrationConstants.APP_NAME,
                    getDirectorId() + "_" + customer.getName() + "_" + accList.getName());
            rulesMap.put("source-object", accListRule.getSourceObject());
            rulesMap.put("source-condition", StringUtils.isEmpty(accListRule.getSourceCondition())
                    ? accListRule.getSourceCondition() : accListRule.getSourceCondition().toUpperCase());
            String protocol = StringUtils.isEmpty(accListRule.getProtocol()) ? accListRule.getProtocol()
                    : accListRule.getProtocol().toUpperCase();
            rulesMap.put("protocol", protocol);
            rulesMap.put("uniqueId",
                    getDirectorId() + "_" + customer.getName() + "_" + accList.getName() + "_" + protocol + "_" + i);
            rulesMap.put("destination-condition", StringUtils.isEmpty(accListRule.getDestinationCondition())
                    ? accListRule.getDestinationCondition() : accListRule.getDestinationCondition().toUpperCase());
            rulesMap.put("port-number", String.valueOf(accListRule.getPortNumber()));
            rulesMap.put("destination-object", accListRule.getDestinationObject());
            rulesMap.put(AnutaIntegrationConstants.POOL_NAME, getDirectorId() + AnutaIntegrationConstants.POOL);
            rulesResult.add(rulesMap);
            if (!StringUtils.isEmpty(protocol)) {
                protocolMap.put("protocol", protocol);
                protocolEnumData.add(protocolMap);
            }
            if (!StringUtils.isEmpty(accListRule.getSourceCondition())) {
                appConditionMap.put("appCondition", accListRule.getSourceCondition().toUpperCase());
                appConditionEnumData.add(appConditionMap);
            }
        }

    }

    public AnutaApiService getAnutaApiService() {
        return anutaApiService;
    }

    public void setAnutaApiService(AnutaApiService anutaApiService) {
        this.anutaApiService = anutaApiService;
    }

    public MessageChannel getRawFragmentMessageInputChannel() {
        return rawFragmentMessageInputChannel;
    }

    public void setRawFragmentMessageInputChannel(MessageChannel rawFragmentMessageInputChannel) {
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
    }

    public String getAllDevicesUrl() {
        return allDevicesUrl;
    }

    public void setAllDevicesUrl(String allDevicesUrl) {
        this.allDevicesUrl = allDevicesUrl;
    }

    public String getPrimeAppsUrl() {
        return primeAppsUrl;
    }

    public void setPrimeAppsUrl(String primeAppsUrl) {
        this.primeAppsUrl = primeAppsUrl;
    }

    public String getAllSitesUrl() {
        return allSitesUrl;
    }

    public void setAllSitesUrl(String allSitesUrl) {
        this.allSitesUrl = allSitesUrl;
    }

    public String getCustomAppsUrl() {
        return customAppsUrl;
    }

    public void setCustomAppsUrl(String customAppsUrl) {
        this.customAppsUrl = customAppsUrl;
    }

    public String getDeviceClassMapUrl() {
        return deviceClassMapUrl;
    }

    public void setDeviceClassMapUrl(String deviceClassMapUrl) {
        this.deviceClassMapUrl = deviceClassMapUrl;
    }

    public AnutaApi getAnutaApi() {
        return anutaApi;
    }

    @Required
    public void setAnutaApi(AnutaApi versaApi) {
        this.anutaApi = versaApi;
    }

    public String getDirectorId() {
        return directorId;
    }

    public void setDirectorId(String directorId) {
        this.directorId = directorId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public CreateFeedPoolService getCreateFeedPoolService() {
        return createFeedPoolService;
    }

    public void setCreateFeedPoolService(CreateFeedPoolService createFeedPoolService) {
        this.createFeedPoolService = createFeedPoolService;
    }

    public EventPublicationService getEventPublicationService() {
        return eventPublicationService;
    }

    public void setEventPublicationService(EventPublicationService eventPublicationService) {
        this.eventPublicationService = eventPublicationService;
    }
}
