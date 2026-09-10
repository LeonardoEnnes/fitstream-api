package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.model.Meal;
import com.dev.fitstream.nutrition.domain.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllMealsUseCase {
    private final MealRepository mealRepository;

    public FindAllMealsUseCase(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> execute() {
        return mealRepository.findAll();
    }
}
