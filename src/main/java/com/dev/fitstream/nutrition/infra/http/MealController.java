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
    @Operation(summary = "Registra uma nova refeição", description = "Adiciona uma nova refeição ao diário de consumo diário.")
    public ResponseEntity<CreateMealUseCase.Output> createMeal(@RequestBody CreateMealUseCase.Input input) {
        CreateMealUseCase.Output output = createMealUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping
    @Operation(summary = "Lista todas as refeições", description = "Retorna o histórico completo de refeições cadastradas no diário.")
    public ResponseEntity<List<MealResponse>> findAllMeals() {
        List<MealResponse> meals = findAllMealsUseCase.execute().stream()
            .map(m -> new MealResponse(m.getId().toString(), m.getName(), m.getDescription(), m.getConsumedAt().toString()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(meals);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma refeição", description = "Deleta uma refeição do diário com base no seu identificador único (UUID).")
    public ResponseEntity<Void> deleteMeal(@PathVariable UUID id) {
        deleteMealUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @Schema(description = "Representação da refeição listada no diário")
    public record MealResponse(
        @Schema(description = "ID único", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,
        @Schema(description = "Nome da refeição", example = "Almoço")
        String name,
        @Schema(description = "Descrição", example = "Arroz, feijão e frango grelhado")
        String description,
        @Schema(description = "Data do consumo", example = "2026-09-05T12:30:00")
        String consumedAt
    ) {}
}
