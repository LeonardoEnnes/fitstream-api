package com.dev.fitstream.nutrition.infra.http;

import com.dev.fitstream.nutrition.application.usecase.GetDashboardSummaryUseCase;
import com.dev.fitstream.nutrition.infra.http.LiveFeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "Visão geral de macros e streaming de eventos em tempo real")
public class DashboardController {

    private final GetDashboardSummaryUseCase summaryUseCase;
    private final LiveFeedService liveFeedService;

    public DashboardController(GetDashboardSummaryUseCase summaryUseCase, LiveFeedService liveFeedService) {
        this.summaryUseCase = summaryUseCase;
        this.liveFeedService = liveFeedService;
    }

    @GetMapping("/summary")
    @Operation(summary = "Obter resumo do dashboard", description = "Retorna os totais consolidados de calorias e macronutrientes do dia.")
    public ResponseEntity<GetDashboardSummaryUseCase.Output> getSummary() {
        return ResponseEntity.ok(summaryUseCase.execute());
    }

    @GetMapping(value = "/live-feed", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "Live Feed em tempo real", description = "Endpoint SSE (Server-Sent Events) mantido aberto para transmitir eventos do Kafka para o front-end.")
    public SseEmitter streamLiveFeed() {
        return liveFeedService.subscribe();
    }
}
