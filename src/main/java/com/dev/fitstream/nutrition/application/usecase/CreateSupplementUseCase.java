package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.repository.SupplementRepository;
import com.dev.fitstream.shared.application.port.out.EventPublisher;
import com.dev.fitstream.nutrition.domain.model.Supplement;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateSupplementUseCase {

    private final SupplementRepository supplementRepository;
    private final EventPublisher eventPublisher;

    public CreateSupplementUseCase(SupplementRepository supplementRepository, EventPublisher eventPublisher) {
        this.supplementRepository = supplementRepository;
        this.eventPublisher = eventPublisher;
    }

    @Schema(name = "CreateSupplementInput", description = "Dados para registro de suplementação")
    public record Input(
        @Schema(description = "Nome do suplemento", example = "Whey Protein")
        String name,

        @Schema(description = "Quantidade consumida", example = "30")
        int dosage,

        @Schema(description = "Unidade de medida", example = "g")
        String unit
    ) {}

    @Schema(name = "CreateSupplementOutput", description = "Suplemento registrado com sucesso")
    public record Output(
        @Schema(description = "ID único do suplemento", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,

        @Schema(description = "Nome do suplemento", example = "Whey Protein")
        String name,

        @Schema(description = "Quantidade consumida", example = "30")
        int dosage,

        @Schema(description = "Unidade de medida", example = "g")
        String unit,

        @Schema(description = "Data e hora do registro", example = "2026-09-10T08:30:00")
        String takenAt
    ) {}

    public Output execute(Input input) {
        if (input.name() == null || input.name().isBlank()) {
            throw new IllegalArgumentException("O nome do suplemento é obrigatório.");
        }
        if (input.dosage() <= 0) {
            throw new IllegalArgumentException("A dosagem deve ser maior que zero.");
        }

        Supplement supplement = new Supplement(null, input.name(), input.dosage(), input.unit(), LocalDateTime.now());
        Supplement saved = supplementRepository.save(supplement);

        eventPublisher.publishLiveFeedEvent("SUPLEMENTAÇÃO", "Suplemento consumido: " + saved.getName());

        return new Output(
            saved.getId().toString(),
            saved.getName(),
            saved.getDosage(),
            saved.getUnit(),
            saved.getTakenAt().toString()
        );
    }
}
