package com.sap.tcl.avalon.user.inbound.listeners;

import java.util.Arrays;


import org.springframework.beans.factory.annotation.Required;

import com.hybris.datahub.api.event.CompositionCompletedEvent;
import com.hybris.datahub.api.event.DataHubEventListener;
import com.hybris.datahub.api.event.InitiatePublicationEvent;
import com.hybris.datahub.service.EventPublicationService;
import com.hybris.datahub.service.impl.AbstractPoolEventListener;

public class UserCompositionCompletedListener extends AbstractPoolEventListener
        implements DataHubEventListener<CompositionCompletedEvent> {

    private EventPublicationService eventPublicationService;

    @Override
    public void handleEvent(final CompositionCompletedEvent event) {

        final InitiatePublicationEvent publishEvent = new InitiatePublicationEvent(
                event.getPoolId(), Arrays.asList("HybrisCore"));
        eventPublicationService.publishEvent(publishEvent);
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
    public void setEventPublicationService(
            final EventPublicationService eventPublicationService) {
        this.eventPublicationService = eventPublicationService;
    }
}
