package com.sap.tcl.avalon.anuta.inbound.services;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hybris.datahub.service.DataHubFeedService;
import com.sap.tcl.avalon.anuta.inbound.constants.AnutaIntegrationConstants;

/**
 * 
 * @author TO-OW-16
 *
 */
public class CreateFeedPoolService {

    private static final Logger LOG = LoggerFactory.getLogger(CreateFeedPoolService.class);
    private DataHubFeedService feedService;

    /**
     * 
     * @param dirId
     */
    public void checkFeedsAndPools(String dirId) {
        String[] feedsArray = new String[] { "DEVICE_FEED", "SITES_FEED", "CUSTOM_APPS_FEED", "RAW_APPS_FEED",
                "DEVICE_CLASS_MAP_FEED" };
        List<String> feeds = Arrays.asList(feedsArray);
        for (String feedName : feeds) {
            if (getFeedService().findDataFeedByName(dirId + "_" + feedName) == null) {
                getFeedService().createFeed(dirId + "_" + feedName, dirId + "_POOL", null, null, "NAMED_POOL",
                        dirId + "_" + feedName + " for pool " + dirId + "_POOL");
                LOG.info(":::::::{}_{}  for pool {}_POOL Created::::::::::::", dirId, feedName, dirId);
            } else {
                LOG.info("::::::::::::::{} POOL Existed::::::::::::::::::::::::",
                        dirId + AnutaIntegrationConstants.POOL);
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
