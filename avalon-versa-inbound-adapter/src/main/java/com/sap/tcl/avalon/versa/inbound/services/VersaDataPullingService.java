package com.sap.tcl.avalon.versa.inbound.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.hybris.datahub.api.event.InitiateCompositionEvent;
import com.hybris.datahub.service.EventPublicationService;
import com.sap.tcl.avalon.utils.AvalonUtils;
import com.sap.tcl.avalon.versa.inbound.constants.VersaIntegrationConstants;
import com.sap.tcl.avalon.versa.inbound.dtos.Template;
import com.sap.tcl.avalon.versa.inbound.dtos.VersanmstemplatesResponse;

import reactor.util.CollectionUtils;

/**
 * 
 * @author TO-OW-16
 *
 */
public class VersaDataPullingService {

    private static final Logger LOG = LoggerFactory.getLogger(VersaDataPullingService.class);

    private VersaApi versaApi;

    private MessageChannel rawFragmentMessageInputChannel;

    private CreateFeedPoolService createFeedPoolService;

    private String templateUrl;
    private String sdwanOrgUrl;
    private String deviceGroupUrl;
    private String deviceGroupTemplatesUrl;
    private String deviceDetailsUrl;
    private String deviceStatusUrl;
    private String appsUrl;
    private String preUrlsCategories;

    private String urlsCategoriesPerTemplate;
    private String forwardProfilesUrl;
    private String qosForwardProfilesUrl;
    private String qosPolicyUrl;
    private String policyGroupUrl;
    private String slrProfileUrl;
    private String userDefinedAppsUrl;

    private String appsQosRulesUrl;
    private String rulesPerPolicy;
    private String userDefinedAppsDetails;
    private String directorId;
    private String baseUrl;
    private String userName;
    private String password;
    private String pollTime;
    private static final String POOL = "_POOL";
    private static final String POOLNAME = "poolName";

    private EventPublicationService eventPublicationService;

    public static final String TASK_RESP_MEG = "Completed mesg \n" + "status: {}\n" + "Feed Name: {}\n" + "RawType: {}";

    /**
     * @throws Exception
     * 
     */
    public void pull() {
        ExecutorService executor = Executors.newFixedThreadPool(8);

        LOG.info(":::::::::::::::::::Pulling Data::::::::::::::::::::::::::::");
        if (LOG.isDebugEnabled()) {
            LOG.debug("############# Starting verasa pull worker ###########");
        }

        pollTime = String.valueOf(System.currentTimeMillis());

        LOG.info("Data Pulling Time : {}", getPollTime());

        getCreateFeedPoolService().checkFeedsAndPools(getDirectorId());
        boolean templateStatus = false;
        try {
            templateStatus = loadTemplatesToPool();
        } catch (Exception e1) {
            LOG.error("Error While Pulling Templates Data cause is {}", e1);
        }
        try {
            List<Callable<Integer>> callables = Arrays.asList(
                    () -> new SdwanOrganizationCallable(this.sdwanOrgUrl, this.versaApi,
                            this.rawFragmentMessageInputChannel, getDirectorId()).call(),
                    () -> new DeviceGroupsCallable(this.deviceGroupUrl, this.versaApi,
                            this.rawFragmentMessageInputChannel, this.getDirectorId()).call(),
                    () -> new DeviceDetailsCallable(this.deviceDetailsUrl, this.versaApi,
                            this.rawFragmentMessageInputChannel, this.getDirectorId()).call(),
                    () -> new DeviceGrpTemplateCallable(this.deviceGroupTemplatesUrl, this.versaApi,
                            this.rawFragmentMessageInputChannel, this.getDirectorId()).call(),
                    () -> new PreUrlCategoryCallable(this.preUrlsCategories, this.versaApi,
                            this.rawFragmentMessageInputChannel, this.getDirectorId()).call(),
                    () -> new PreApplicationsCallable(this.appsUrl, this.versaApi, this.rawFragmentMessageInputChannel,
                            this.getDirectorId()).call(),
                    () -> new DeviceStatusCallable(this.deviceStatusUrl, this.versaApi,
                            this.rawFragmentMessageInputChannel, this.getDirectorId()).call());

            boolean isCompletedAllTasks = true;

            List<Future<Integer>> futures = executor.invokeAll(callables);
            int taskSumOfResult = 0;
            for (Future<Integer> f : futures) {
                taskSumOfResult += f.get();
                if (!f.isDone()) {
                    isCompletedAllTasks = f.isDone();
                }
            }
            if (isCompletedAllTasks && taskSumOfResult == futures.size() && templateStatus) {
                awaitTerminationAfterShutdown(executor);
                LOG.info("Pulling SUCCESS : {}", futures.size());
            } else {
                LOG.info(":::::::::::Pulling FAILED:::::::::::::");
            }

        } catch (final InterruptedException | ExecutionException e) {
            LOG.info("***VersaPullServiceWorker PullingFailed ********{}", e);
            if (e.getMessage() == null) {
                LOG.info("Error while getting error massage");
            }
        } finally {
            LOG.info("::::::::::::::::::::::::::::::::Firing DataHub Event:::::::::::::::::::::::::::::::::::::::");
            eventPublicationService.publishEvent(
                    new InitiateCompositionEvent(getCreateFeedPoolService().getPoolIdbyName(getDirectorId() + POOL)));
        }
    }

    /**
     * Method to get templates
     * 
     * @return
     * @throws Exception
     */
    public boolean loadTemplatesToPool()  {
        boolean isCompleted = false;
        String reString = null;
        try {
            LOG.info("::::::::::::::::::Pulling Templates Data Started::::::::::::::::::::::::");
            reString = versaApi.versaGetCall(templateUrl, new HashMap<String, String>(), String.class);
            LOG.info(reString);
        } catch (Exception e) {
            LOG.error("Error while Pulling data using the api url {} {}",
                    AvalonUtils.buildPathParamUrl(baseUrl + templateUrl, null), e);
        }
        if (StringUtils.isNotBlank(reString)) {
            VersanmstemplatesResponse versanmstemplatesResponse = null;
            try {
                versanmstemplatesResponse = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString,
                        VersanmstemplatesResponse.class);
            } catch (Exception e) {
                LOG.error("Error While Throwing Exception", e);
            }
            if (versanmstemplatesResponse != null && null != versanmstemplatesResponse.getVersanmsTemplates()) {
                LOG.info("::::::::::::::::Templates Conversion Started::::::::::::::::::::::::::::");
                List<Template> templateList = versanmstemplatesResponse.getVersanmsTemplates().getTemplate();
                if (!CollectionUtils.isEmpty(templateList)) {
                    List<Map<String, String>> response = new ArrayList<>();
                    getTemplates(templateList, response);
                    isCompleted = this.rawFragmentMessageInputChannel
                            .send(new GenericMessage<List<Map<String, String>>>(response,
                                    AvalonUtils.constructMessageHeader(VersaIntegrationConstants.TEMPLATE_RAW,
                                            getDirectorId() + "_" + VersaIntegrationConstants.TEMPLATES_FEED)));
                    LOG.info("::::::::::::Templates Loaded Successfully::::::::::::::::");
                    pushPollStartTime(getDirectorId(), pollTime, templateList);
                    isCompleted = postTemplateDependency(templateList);
                }
            } else {
                LOG.error("::::::::::::Template Data Conversion Failed:::::::::::::::");
            }
        } else {
            LOG.error(VersaIntegrationConstants.EMPTY_RES_MEG, templateUrl);
            isCompleted = true;
        }
        int resposne = 0;
        if (isCompleted) {
            resposne = 1;
        }
        LOG.info("Templates Loaded Successfully for the response", resposne);
        return isCompleted;
    }

    /**
     * Method to get All template related data
     * 
     * @param templateList
     * @return
     * @throws InterruptedException
     */

    public boolean postTemplateDependency(List<Template> templateList)  {

        ExecutorService templateEXECUTOR = Executors.newFixedThreadPool(7);
        LOG.info(":::::::::::::::::Pulling Template Dependancy Data::::::::::::::::::");
        List<Callable<Integer>> callables = Arrays.asList(
                () -> new AppsQosPolicyCallable(qosPolicyUrl, appsQosRulesUrl,
                        VersaIntegrationConstants.APP_QOS_POLICY_FEED, versaApi, rawFragmentMessageInputChannel,
                        templateList, getDirectorId()).call(),
                () -> new ForwardProfileCallable(forwardProfilesUrl, VersaIntegrationConstants.FORWARDING_PROFILE_FEED,
                        versaApi, rawFragmentMessageInputChannel, templateList, getDirectorId()).call(),
                () -> new PolicyGroupDataCallable(rulesPerPolicy, policyGroupUrl,
                        VersaIntegrationConstants.POLICY_GROUP_FEED, versaApi, rawFragmentMessageInputChannel,
                        templateList, getDirectorId()).call(),
                () -> new QosForwardProfilesCallable(qosForwardProfilesUrl,
                        VersaIntegrationConstants.APP_QOS_PROFILE_FEED, versaApi, rawFragmentMessageInputChannel,
                        templateList, getDirectorId()).call(),
                () -> new SlrProfileCallable(slrProfileUrl, VersaIntegrationConstants.SLA_PROFILE_FEED, versaApi,
                        rawFragmentMessageInputChannel, templateList, getDirectorId()).call(),
                () -> new UrlsCategoriesPerTemplateCallable(urlsCategoriesPerTemplate,
                        VersaIntegrationConstants.URL_CATEGORY_FEED, versaApi, rawFragmentMessageInputChannel,
                        templateList, getDirectorId()).call(),
                () -> new UserDefinedAppsCallable(rawFragmentMessageInputChannel, versaApi,
                        VersaIntegrationConstants.USER_DEFINED_APPS_FEED, templateList, userDefinedAppsUrl,
                        userDefinedAppsDetails, getDirectorId()).call());

        List<Future<Integer>> futures = null;
        
        try {
            futures = templateEXECUTOR.invokeAll(callables);
        } catch (InterruptedException e1) {
           LOG.info("Error while invoking future objects.cause is {}",e1);
        }
        boolean isCompletedAllTasks = true;
        int taskSumOfResult = 0;
        for (Future<Integer> f : futures) {
            try {
                    taskSumOfResult += f.get();
                    if (!f.isDone()) {
                        isCompletedAllTasks = f.isDone();
                    }
            } catch (Exception e) {
                LOG.error(":::::Failed at Future Computation:::", e);
            }
           
        }
        if (isCompletedAllTasks && taskSumOfResult == (futures!=null ?  futures.size() : 0)) {
            awaitTerminationAfterShutdown(templateEXECUTOR);
            LOG.info("TemplateDependency SUCCESS");
        } else {
            LOG.info("::::::::Pulling Template Dependency Data  FAILED::::::::::::::::");
        }
        return isCompletedAllTasks;
    }

    private void getTemplates(List<Template> templateList, List<Map<String, String>> response) {
        Map<String, String> eachRecord = null;
        for (Template template : templateList) {
            eachRecord = new LinkedHashMap<>();
            eachRecord.put("versanms.templates_template_name", template.getName());
            eachRecord.put("versanms.templates_template_templateType", template.getTemplateType());
            eachRecord.put("versanms.templates_template_isPrimary", String.valueOf(template.getIsPrimary()));
            eachRecord.put("versanms.templates_template_organization", template.getOrganization());
            eachRecord.put("versanms.templates_template_versionNo", template.getVersionNo());
            eachRecord.put(POOLNAME, getDirectorId() + POOL);
            eachRecord.put("dirId", getDirectorId());
            eachRecord.put("uid", getDirectorId() + "_" + template.getOrganization() + "_" + template.getName());
            response.add(eachRecord);
        }
    }

    public void setDeviceStatusUrl(String deviceStatusUrl) {
        this.deviceStatusUrl = deviceStatusUrl;
    }

    public void setAppsUrl(String appsUrl) {
        this.appsUrl = appsUrl;
    }

    /**
     * Method to create pulling start time
     * 
     * @param dirName
     * @param poolTime
     * @param templateList
     */
    public void pushPollStartTime(String dirName, String poolTime, List<Template> templateList) {

        List<Map<String, String>> dirTimes = new ArrayList<>();
        Map<String, String> mapTime = new HashMap<>();
        mapTime.put("dirName", dirName);
        mapTime.put("pollStartTime", poolTime);
        mapTime.put(POOLNAME, getDirectorId() + POOL);
        dirTimes.add(mapTime);
        this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(dirTimes,
                AvalonUtils.constructMessageHeader(VersaIntegrationConstants.DIR_POLL_TIME,
                        getDirectorId() + "_" + VersaIntegrationConstants.TEMPLATES_FEED)));

        List<Map<String, String>> sdwanSystems = new ArrayList<>();
        Map<String, String> sdwansystem = new HashMap<>();

        for (Template template : templateList) {
            sdwansystem.put("dirName", dirName.split("_")[2]);
            sdwansystem.put(POOLNAME, getDirectorId());
            sdwansystem.put("organization", template.getOrganization());
            sdwansystem.put("userName", getUserName());
            sdwansystem.put("password", getPassword());
            sdwansystem.put("base-url", getBaseUrl());
            sdwanSystems.add(sdwansystem);
        }
        this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(sdwanSystems,
                AvalonUtils.constructMessageHeader(VersaIntegrationConstants.SDWAN_SYSTEM,
                        getDirectorId() + "_" + VersaIntegrationConstants.TEMPLATES_FEED)));
    }

    /**
     * Method to
     * 
     * @param threadPool
     */
    public void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (Exception ex) {
            LOG.info("cause is {}", ex);
            threadPool.shutdownNow();
        }
    }

    public VersaApi getVersaApi() {
        return versaApi;
    }

    @Required
    public void setVersaApi(VersaApi versaApi) {
        this.versaApi = versaApi;
    }

    @Required
    public void setRawFragmentMessageInputChannel(MessageChannel rawFragmentMessageInputChannel) {
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
    }

    @Required
    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    @Required
    public void setSdwanOrgUrl(String sdwanOrgUrl) {
        this.sdwanOrgUrl = sdwanOrgUrl;
    }

    @Required
    public void setDeviceGroupUrl(String deviceGroupUrl) {
        this.deviceGroupUrl = deviceGroupUrl;
    }

    @Required
    public void setDeviceGroupTemplatesUrl(String deviceGroupTemplatesUrl) {
        this.deviceGroupTemplatesUrl = deviceGroupTemplatesUrl;
    }

    @Required
    public void setDeviceDetailsUrl(String deviceDetailsUrl) {
        this.deviceDetailsUrl = deviceDetailsUrl;
    }

    @Required
    public void setUrlsCategoriesPerTemplate(String urlsCategoriesPerTemplate) {
        this.urlsCategoriesPerTemplate = urlsCategoriesPerTemplate;
    }

    @Required
    public void setForwardProfilesUrl(String forwardProfilesUrl) {
        this.forwardProfilesUrl = forwardProfilesUrl;
    }

    @Required
    public void setQosForwardProfilesUrl(String qosForwardProfilesUrl) {
        this.qosForwardProfilesUrl = qosForwardProfilesUrl;
    }

    @Required
    public void setPolicyGroupUrl(String policyGroupUrl) {
        this.policyGroupUrl = policyGroupUrl;
    }

    @Required
    public void setSlrProfileUrl(String slrProfileUrl) {
        this.slrProfileUrl = slrProfileUrl;
    }

    @Required
    public void setUserDefinedAppsUrl(String userDefinedAppsUrl) {
        this.userDefinedAppsUrl = userDefinedAppsUrl;
    }

    @Required
    public void setRulesPerPolicy(String rulesPerPolicy) {
        this.rulesPerPolicy = rulesPerPolicy;
    }

    @Required
    public void setUserDefinedAppsDetails(String userDefinedAppsDetails) {
        this.userDefinedAppsDetails = userDefinedAppsDetails;
    }

    @Required
    public void setPreUrlsCategories(String preUrlsCategories) {
        this.preUrlsCategories = preUrlsCategories;
    }

    @Required
    public void setQosPolicyUrl(String qosPolicyUrl) {
        this.qosPolicyUrl = qosPolicyUrl;
    }

    @Required
    public void setAppsQosRulesUrl(String appsQosRulesUrl) {
        this.appsQosRulesUrl = appsQosRulesUrl;
    }

    public CreateFeedPoolService getCreateFeedPoolService() {
        return createFeedPoolService;
    }

    public void setCreateFeedPoolService(CreateFeedPoolService createFeedPoolService) {
        this.createFeedPoolService = createFeedPoolService;
    }

    @Required
    public String getDirectorId() {
        return directorId;
    }

    @Required
    public void setDirectorId(String directorId) {
        this.directorId = directorId;
    }

    @Required
    public String getBaseUrl() {
        return baseUrl;
    }

    @Required
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Required
    public String getUserName() {
        return userName;
    }

    @Required
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Required
    public String getPassword() {
        return password;
    }

    @Required
    public void setPassword(String password) {
        this.password = password;
    }

    @Required
    public EventPublicationService getEventPublicationService() {
        return eventPublicationService;
    }

    @Required
    public void setEventPublicationService(EventPublicationService eventPublicationService) {
        this.eventPublicationService = eventPublicationService;
    }

    public String getPollTime() {
        return pollTime;
    }

    public void setPollTime(String pollTime) {
        this.pollTime = pollTime;
    }
}
