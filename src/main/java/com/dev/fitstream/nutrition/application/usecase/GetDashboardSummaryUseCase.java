package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.model.Meal;
import com.dev.fitstream.nutrition.domain.repository.MealRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetDashboardSummaryUseCase {

    private final MealRepository mealRepository;

    public GetDashboardSummaryUseCase(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public record TimelinePoint(String time, int calories) {}

    @Schema(name = "DashboardSummaryOutput", description = "Resumo diário de macros e calorias para o dashboard")
    public record Output(
        int totalCalories,
        int calorieGoal,
        int totalProtein,
        int totalCarbs,
        int totalFat,
        List<TimelinePoint> timeline
    ) {}

    @SuppressWarnings("null")
    public Output execute() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        List<Meal> todaysMeals = mealRepository.findByConsumedAtBetween(startOfDay, endOfDay);

        int totalCalories = todaysMeals.stream().mapToInt(Meal::getCalories).sum();
        int totalProtein = todaysMeals.stream().mapToInt(Meal::getProtein).sum();
        int totalCarbs = todaysMeals.stream().mapToInt(Meal::getCarbs).sum();
        int totalFat = todaysMeals.stream().mapToInt(Meal::getFat).sum();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        List<TimelinePoint> timeline = todaysMeals.stream()
            .sorted(Comparator.comparing(Meal::getConsumedAt))
            .map(m -> new TimelinePoint(m.getConsumedAt().format(timeFormatter), m.getCalories()))
            .collect(Collectors.toList());

        return new Output(totalCalories, 2500, totalProtein, totalCarbs, totalFat, timeline);
    }
}
