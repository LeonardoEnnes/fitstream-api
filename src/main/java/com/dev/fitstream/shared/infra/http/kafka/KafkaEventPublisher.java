package com.dev.fitstream.shared.infra.http.kafka;

import com.dev.fitstream.shared.application.port.out.EventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Component
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "live-feed";

    public KafkaEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishLiveFeedEvent(String type, String message) {
        // Formato estruturado para facilitar a leitura pelo Front-end
        Map<String, Object> payload = Map.of(
            "id", UUID.randomUUID().toString(),
            "timestamp", LocalDateTime.now().toString(),
            "type", type, // Ex: "NUTRITION", "WORKOUT"
            "message", message
        );
        System.out.println(">>> ENVIANDO EVENTO PARA O KAFKA: " + message);
        kafkaTemplate.send(TOPIC, payload);
    }
}
