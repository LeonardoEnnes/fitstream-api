package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.model.Meal;
import com.dev.fitstream.nutrition.domain.repository.MealRepository;
import com.dev.fitstream.shared.application.port.out.EventPublisher;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class CreateMealUseCase {

    private final MealRepository mealRepository;
    private final EventPublisher eventPublisher; // Injeção do publicador

    public CreateMealUseCase(MealRepository mealRepository, EventPublisher eventPublisher) {
        this.mealRepository = mealRepository;
        this.eventPublisher = eventPublisher;
    }

    @Schema(description = "Dados de entrada para registro de uma refeição")
    public record Input(
        @Schema(description = "Nome da refeição", example = "Café da Manhã")
        String name,

        @Schema(description = "Descrição dos alimentos consumidos", example = "3 ovos mexidos, 2 fatias de pão integral e café com leite")
        String description
    ) {}

    @Schema(description = "Refeição registrada com sucesso")
    public record Output(
        @Schema(description = "ID único da refeição", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,

        @Schema(description = "Nome da refeição", example = "Café da Manhã")
        String name,

        @Schema(description = "Descrição dos alimentos", example = "3 ovos mexidos, 2 fatias de pão integral e café com leite")
        String description,

        @Schema(description = "Data e hora do registro", example = "2026-09-05T08:30:00")
        String consumedAt
    ) {}

    public Output execute(Input input) {
        if (input.name() == null || input.name().isBlank()) {
            throw new IllegalArgumentException("O nome da refeição não pode ser vazio.");
        }

        Meal meal = new Meal(null, input.name(), input.description(), LocalDateTime.now());
        Meal savedMeal = mealRepository.save(meal);

        eventPublisher.publishLiveFeedEvent("NUTRITION", "Nova refeição registrada: " + savedMeal.getName());

        return new Output(
            savedMeal.getId().toString(),
            savedMeal.getName(),
            savedMeal.getDescription(),
            savedMeal.getConsumedAt().toString()
        );
    }
}
