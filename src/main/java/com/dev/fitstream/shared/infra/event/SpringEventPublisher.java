package com.dev.fitstream.shared.infra.event;

import com.dev.fitstream.shared.application.port.out.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishLiveFeedEvent(String type, String message) {
        applicationEventPublisher.publishEvent(new LiveFeedEvent(type, message));
    }
}
