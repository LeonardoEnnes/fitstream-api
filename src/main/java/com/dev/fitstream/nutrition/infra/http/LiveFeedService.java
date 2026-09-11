package com.dev.fitstream.nutrition.infra.http;

import com.dev.fitstream.shared.infra.event.LiveFeedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

    // Escuta o evento nativo do Spring de forma síncrona/assíncrona
    @EventListener
    public void handleLiveFeedEvent(LiveFeedEvent event) {
        try {
            Map<String, Object> payload = Map.of(
                "id", UUID.randomUUID().toString(),
                "timestamp", LocalDateTime.now().toString(),
                "type", event.type(),
                "message", event.message()
            );

            String jsonMessage = objectMapper.writeValueAsString(payload);
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
