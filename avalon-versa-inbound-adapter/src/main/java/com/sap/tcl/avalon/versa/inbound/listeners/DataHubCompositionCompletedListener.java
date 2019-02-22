package com.sap.tcl.avalon.versa.inbound.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.hybris.datahub.api.event.CompositionCompletedEvent;
import com.hybris.datahub.api.event.DataHubEventListener;
import com.hybris.datahub.api.event.InitiatePublicationEvent;
import com.hybris.datahub.service.EventPublicationService;
import com.hybris.datahub.service.impl.AbstractPoolEventListener;

public class DataHubCompositionCompletedListener extends AbstractPoolEventListener
        implements DataHubEventListener<CompositionCompletedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataHubCompositionCompletedListener.class);

    private EventPublicationService eventPublicationService;

    private String pools;

    @Override
    public void handleEvent(final CompositionCompletedEvent event) {

        String[] poolsArray = getPools().split(",");
        List<String> poolsList = new ArrayList<>();
        for (int i = 0; i < poolsArray.length; i++) {
            poolsList.add(poolsArray[i]);
        }
        final String poolName = getPoolNameFromId(event.getPoolId());
        if (poolsList.contains(poolName)) {
            LOGGER.debug("Handling composition completed event for pool : {} ", poolName);
            final InitiatePublicationEvent publishEvent = new InitiatePublicationEvent(event.getPoolId(),
                    Arrays.asList("HybrisCore"));
            eventPublicationService.publishEvent(publishEvent);
        }
    }

    @Override
    public Class<CompositionCompletedEvent> getEventClass() {
        return CompositionCompletedEvent.class;
    }

    @Override
    public boolean executeInTransaction() {
        return true;
    }

    @Required
    public void setEventPublicationService(final EventPublicationService eventPublicationService) {
        this.eventPublicationService = eventPublicationService;
    }

    @Required
    public String getPools() {
        return pools;
    }

    @Required
    public void setPools(String pools) {
        this.pools = pools;
    }
}
