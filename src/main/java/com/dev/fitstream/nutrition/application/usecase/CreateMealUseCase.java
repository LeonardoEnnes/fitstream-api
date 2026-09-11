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
    private final EventPublisher eventPublisher;

    public CreateMealUseCase(MealRepository mealRepository, EventPublisher eventPublisher) {
        this.mealRepository = mealRepository;
        this.eventPublisher = eventPublisher;
    }

    @Schema(description = "Dados de entrada para registro de uma refeição")
    public record Input(
        @Schema(description = "Nome da refeição", example = "Café da Manhã")
        String name,
        int calories,
        int protein,
        int carbs,
        int fat
    ) {}

    @Schema(description = "Refeição registrada com sucesso")
    public record Output(
        @Schema(description = "ID único", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,
        String name,
        int calories,
        int protein,
        int carbs,
        int fat,
        @Schema(description = "Data do consumo", example = "2026-09-05T08:30:00")
        String consumedAt
    ) {}

    public Output execute(Input input) {
        if (input.name() == null || input.name().isBlank()) {
            throw new IllegalArgumentException("O nome da refeição não pode ser vazio.");
        }

        Meal meal = new Meal(
            null,
            input.name(),
            input.calories(),
            input.protein(),
            input.carbs(),
            input.fat(),
            LocalDateTime.now()
        );

        Meal savedMeal = mealRepository.save(meal);

        eventPublisher.publishLiveFeedEvent("NUTRITION", "Nova refeição registrada: " + savedMeal.getName());

        return new Output(
            savedMeal.getId().toString(),
            savedMeal.getName(),
            savedMeal.getCalories(),
            savedMeal.getProtein(),
            savedMeal.getCarbs(),
            savedMeal.getFat(),
            savedMeal.getConsumedAt().toString()
        );
    }
}
