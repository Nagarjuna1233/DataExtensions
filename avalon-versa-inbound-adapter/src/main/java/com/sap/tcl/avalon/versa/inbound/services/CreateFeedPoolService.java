package com.sap.tcl.avalon.versa.inbound.services;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hybris.datahub.service.DataHubFeedService;
import com.sap.tcl.avalon.versa.inbound.constants.VersaIntegrationConstants;

/**
 * 
 * @author TO-OW-16
 *
 */
public class CreateFeedPoolService {

    private static final Logger LOG = LoggerFactory.getLogger(CreateFeedPoolService.class);
    private DataHubFeedService feedService;

    /**
     * Method to check Pool and Pools creation
     * 
     * @param dirId
     */
    public void checkFeedsAndPools(String dirId) {

        String[] feedsArray = new String[] { "TEMPLATES_FEED", "URL_CATEGORY_FEED", "DEVICE_GROUP_FEED",
                "DEVICE_DETAILS_FEED", "ORGANIZATION_FEED", "POLICY_GROUP_FEED", "RULES_PER_POLICY_FEED",
                "FORWARDING_PROFILE_FEED", "APP_QOS_PROFILE_FEED", "SLA_PROFILE_FEED", "USER_DEFINED_APP_DETAILS_FEED",
                "USER_DEFINED_APPS_FEED", "DEVICE_GRP_TEMPMAP_FEED", "APP_QOS_POLICY_FEED", "APPQOS_RULE_FEED",
                "DIRECTORY_POLL_TIME_FEED", "PRE_APPLICATION_FEED", "DEVICE_STATUS_FEED", "CIRCUT_PRIORITY_FEED" };
        List<String> feeds = Arrays.asList(feedsArray);

        for (String feedName : feeds) {
            if (getFeedService().findDataFeedByName(dirId + "_" + feedName) == null) {
                getFeedService().createFeed(dirId + "_" + feedName, dirId + VersaIntegrationConstants.POOL, null, null,
                        "NAMED_POOL", dirId + "_" + feedName + " for pool " + dirId + VersaIntegrationConstants.POOL);
                LOG.info(":::::::{}_{}  for pool {}_POOL Created::::::::::::", dirId, feedName, dirId);
            } else {
                LOG.info("::::::::::::::{} POOL Existed::::::::::::::::::::::::",
                        dirId + VersaIntegrationConstants.POOL);
            }
        }
    }

    /**
     * 
     * @param poolName
     * @return
     */
    public Long getPoolIdbyName(String poolName) {
        return getFeedService().findPoolByName(poolName).getPoolId();
    }

    public DataHubFeedService getFeedService() {
        return feedService;
    }

    public void setFeedService(DataHubFeedService feedService) {
        this.feedService = feedService;
    }
}
