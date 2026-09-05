package com.dev.fitstream.nutrition.infra.http;

import com.dev.fitstream.nutrition.application.usecase.CreateMealUseCase;
import com.dev.fitstream.nutrition.domain.model.Meal;
import com.dev.fitstream.nutrition.domain.repository.MealRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/meals")
public class MealController {

    private final CreateMealUseCase createMealUseCase;
    private final MealRepository mealRepository;

    public MealController(CreateMealUseCase createMealUseCase, MealRepository mealRepository) {
        this.createMealUseCase = createMealUseCase;
        this.mealRepository = mealRepository;
    }

    @PostMapping
    public ResponseEntity<CreateMealUseCase.Output> createMeal(@RequestBody CreateMealUseCase.Input input) {
        CreateMealUseCase.Output output = createMealUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping
    public ResponseEntity<List<MealResponse>> findAllMeals() {
        List<MealResponse> meals = mealRepository.findAll().stream()
            .map(m -> new MealResponse(m.getId().toString(), m.getName(), m.getDescription(), m.getConsumedAt().toString()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(meals);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable UUID id) {
        mealRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

    public record MealResponse(String id, String name, String description, String consumedAt) {}
}
