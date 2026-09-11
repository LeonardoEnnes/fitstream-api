package com.dev.fitstream.shared.infra.http.kafka;

import com.dev.fitstream.shared.application.port.out.EventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Map;

@Component
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "live-feed";

    public KafkaEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishLiveFeedEvent(String type, String message) {
        Map<String, Object> payload = Map.of(
            "id", UUID.randomUUID().toString(),
            "timestamp", LocalDateTime.now().toString(),
            "type", type,
            "message", message
        );

        // Envio assíncrono em background para nunca bloquear a thread do UseCase/HTTP
        CompletableFuture.runAsync(() -> {
            try {
                kafkaTemplate.send(TOPIC, payload);
            } catch (Exception e) {
                System.err.println(">>> Falha ao enviar evento para o Kafka em background: " + e.getMessage());
            }
        });
    }
}