package com.sap.tcl.avalon.user.inbound.services;

import com.hybris.datahub.api.event.InitiateCompositionEvent;
import com.hybris.datahub.service.DataHubFeedService;
import com.hybris.datahub.service.EventPublicationService;

public class EventServiceWorker {

    private DataHubFeedService dataHubFeedService;
    private EventPublicationService eventPublicationService;

    public void pullEvent() {
        Long poolId = getDataHubFeedService().findPoolByName("GLOBAL")
                .getPoolId();
        getEventPublicationService().publishEvent(
                new InitiateCompositionEvent(poolId));
    }

    public DataHubFeedService getDataHubFeedService() {
        return dataHubFeedService;
    }

    public void setDataHubFeedService(DataHubFeedService dataHubFeedService) {
        this.dataHubFeedService = dataHubFeedService;
    }

    public EventPublicationService getEventPublicationService() {
        return eventPublicationService;
    }

    public void setEventPublicationService(
            EventPublicationService eventPublicationService) {
        this.eventPublicationService = eventPublicationService;
    }
}
