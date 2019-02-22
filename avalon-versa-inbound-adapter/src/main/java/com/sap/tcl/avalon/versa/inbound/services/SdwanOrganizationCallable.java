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
import com.sap.tcl.avalon.versa.inbound.dtos.OrganisationResponse;
import com.sap.tcl.avalon.versa.inbound.dtos.Organization;

import reactor.util.CollectionUtils;

public class SdwanOrganizationCallable implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(SdwanOrganizationCallable.class);

    private String sdwanOrgUrl;
    private VersaApi versaApi;
    private MessageChannel rawFragmentMessageInputChannel;
    private String dirId;

    public SdwanOrganizationCallable(String sdwanOrgUrl, VersaApi versaApi,
            MessageChannel rawFragmentMessageInputChannel, String dirId) {
        super();
        this.sdwanOrgUrl = sdwanOrgUrl;
        this.versaApi = versaApi;
        this.rawFragmentMessageInputChannel = rawFragmentMessageInputChannel;
        this.dirId = dirId;
    }

    @Override
    public Integer call() throws Exception {
        if (getSdwanOrganizationData()) {
            return 1;
        }
        return 0;
    }

    public boolean getSdwanOrganizationData() {

        String reString = null;
        boolean isCompleted = false;
        try {
            reString = versaApi.versaGetCall(sdwanOrgUrl, new HashMap<String, String>(), String.class);
            LOG.info(reString);
        } catch (Exception e) {
            LOG.info(VersaIntegrationConstants.ERROR_WHILE_PULLING, OrganisationResponse.class.getName(), e);
        }
        if (StringUtils.isNotBlank(reString)) {
            OrganisationResponse orgs = null;
            try {
                orgs = VersaIntegrationConstants.OBJ_MAPPER.readValue(reString, OrganisationResponse.class);
            } catch (Exception e) {
                LOG.error("Error While Converting {} data,cause is {} ", VersaIntegrationConstants.ORGAINZATION_RAW, e);
            }
            if (orgs != null && !CollectionUtils.isEmpty(orgs.getOrganization())) {
                List<Map<String, String>> response = new ArrayList<>();
                getOrganizations(orgs, response);
                isCompleted = this.rawFragmentMessageInputChannel.send(new GenericMessage<List<Map<String, String>>>(
                        response, AvalonUtils.constructMessageHeader(VersaIntegrationConstants.ORGAINZATION_RAW,
                                dirId + "_" + VersaIntegrationConstants.ORGANIZATION_FEED)));
            }
        } else {
            LOG.info(VersaIntegrationConstants.EMPTY_RES_MEG, sdwanOrgUrl);
            isCompleted = true;
        }
        return isCompleted;
    }

    private void getOrganizations(OrganisationResponse orgs, List<Map<String, String>> response) {

        Map<String, String> eachRecord = null;
        for (Organization org : orgs.getOrganization()) {
            String[] cuid = !StringUtils.isBlank(org.getDescription()) ? org.getDescription().split(";")
                    : new String[] {};

            if (cuid.length == 2) {
                eachRecord = new LinkedHashMap<>();
                eachRecord.put("organization_name", org.getName());
                eachRecord.put("organization_uid", cuid[1]);
                eachRecord.put("poolName", dirId + VersaIntegrationConstants.POOL);
                response.add(eachRecord);
            }
        }
    }
}
