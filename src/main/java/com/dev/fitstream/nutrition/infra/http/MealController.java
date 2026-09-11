package com.dev.fitstream.nutrition.infra.http;

import com.dev.fitstream.nutrition.application.usecase.CreateMealUseCase;
import com.dev.fitstream.nutrition.application.usecase.FindAllMealsUseCase;
import com.dev.fitstream.nutrition.application.usecase.DeleteMealUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/meals")
@Tag(name = "Nutrition", description = "Endpoints para gerenciamento do diário de consumo e refeições")
public class MealController {

    private final CreateMealUseCase createMealUseCase;
    private final FindAllMealsUseCase findAllMealsUseCase;
    private final DeleteMealUseCase deleteMealUseCase;

    public MealController(
        CreateMealUseCase createMealUseCase,
        FindAllMealsUseCase findAllMealsUseCase,
        DeleteMealUseCase deleteMealUseCase
    ) {
        this.createMealUseCase = createMealUseCase;
        this.findAllMealsUseCase = findAllMealsUseCase;
        this.deleteMealUseCase = deleteMealUseCase;
    }

    @PostMapping
    @Operation(summary = "Registra uma nova refeição")
    public ResponseEntity<CreateMealUseCase.Output> createMeal(@RequestBody CreateMealUseCase.Input input) {
        CreateMealUseCase.Output output = createMealUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping
    @Operation(summary = "Lista todas as refeições")
    public ResponseEntity<List<MealResponse>> findAllMeals() {
        List<MealResponse> meals = findAllMealsUseCase.execute().stream()
            .map(m -> new MealResponse(
                m.getId().toString(),
                m.getName(),
                m.getCalories(),
                m.getProtein(),
                m.getCarbs(),
                m.getFat(),
                m.getConsumedAt().toString()
            ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(meals);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma refeição")
    public ResponseEntity<Void> deleteMeal(@PathVariable UUID id) {
        deleteMealUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @Schema(description = "Representação da refeição listada no diário")
    public record MealResponse(
        String id,
        String name,
        int calories,
        int protein,
        int carbs,
        int fat,
        String consumedAt
    ) {}
}
