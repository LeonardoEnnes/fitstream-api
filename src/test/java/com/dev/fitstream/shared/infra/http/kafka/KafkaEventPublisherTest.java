package com.dev.fitstream.shared.infra.http.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import static org.mockito.ArgumentMatchers.eq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import java.util.Map;

class KafkaEventPublisherTest {

    private KafkaTemplate<String, Object> kafkaTemplateMock;
    private KafkaEventPublisher kafkaEventPublisher;

    @BeforeEach
    void setUp() {
        kafkaTemplateMock = (KafkaTemplate<String, Object>) mock(KafkaTemplate.class);
        kafkaEventPublisher = new KafkaEventPublisher(kafkaTemplateMock);
    }

    @Test
    @DisplayName("Should send event for topic using kafkatemplate")
    void shouldPublishLiveFeedEventSuccessfully() throws InterruptedException {
        String type = "NUTRITION";
        String message = "Nova refeição adicionada";

        kafkaEventPublisher.publishLiveFeedEvent(type, message);

        // envio sincrono -> breve respiro para a thread em background executar o mock
        Thread.sleep(100);

        verify(kafkaTemplateMock, times(1)).send(eq("live-feed"), any(Map.class));
    }
}
