package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.repository.MealRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Service;

@Service
public class GetDashboardSummaryUseCase {

    private final MealRepository mealRepository;

    public GetDashboardSummaryUseCase(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Schema(name = "DashboardSummaryOutput", description = "Resumo diário de macros e calorias para o dashboard")
    public record Output(
        @Schema(description = "Total de calorias consumidas", example = "1850")
        int totalCalories,
        @Schema(description = "Meta calórica diária", example = "2500")
        int calorieGoal,
        @Schema(description = "Total de proteínas em gramas", example = "140")
        int totalProtein,
        @Schema(description = "Total de carboidratos em gramas", example = "200")
        int totalCarbs,
        @Schema(description = "Total de gorduras em gramas", example = "60")
        int totalFat
    ) {}

    public Output execute() {
        // Lógica de agregação (pode somar das refeições do repositório)
        // Por enquanto, retornamos a estrutura pronta para integrar com o front
        return new Output(1850, 2500, 140, 200, 60);
    }
}
