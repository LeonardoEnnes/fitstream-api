package com.dev.fitstream.nutrition.infra.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class LiveFeedService {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        return emitter;
    }

    // Recebe o Map enviado pelo Kafka
    @KafkaListener(topics = "live-feed", groupId = "fitstream-group")
    public void consumeEvent(Map<String, Object> eventPayload) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(eventPayload);
            System.out.println(">>> EVENTO PROCESSADO E ENVIADO VIA SSE: " + jsonMessage);

            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(SseEmitter.event().name("message").data(jsonMessage));
                } catch (IOException e) {
                    emitters.remove(emitter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
