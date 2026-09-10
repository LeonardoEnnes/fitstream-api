package com.dev.fitstream.shared.application.port.out;

public interface EventPublisher {
    void publishLiveFeedEvent(String type, String message);
}
