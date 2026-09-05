package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.model.Meal;
import com.dev.fitstream.nutrition.domain.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateMealUseCase {

    private final MealRepository mealRepository;

    public CreateMealUseCase(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public record Input(String name, String description) {}
    public record Output(String id, String name, String description, String consumedAt) {}

    public Output execute(Input input) {
        if (input.name() == null || input.name().isBlank()) {
            throw new IllegalArgumentException("O nome da refeição não pode ser vazio.");
        }

        Meal meal = new Meal(null, input.name(), input.description(), LocalDateTime.now());
        Meal savedMeal = mealRepository.save(meal);

        return new Output(
            savedMeal.getId().toString(),
            savedMeal.getName(),
            savedMeal.getDescription(),
            savedMeal.getConsumedAt().toString()
        );

    }
}
