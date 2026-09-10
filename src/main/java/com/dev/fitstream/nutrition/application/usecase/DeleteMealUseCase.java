package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.repository.MealRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class DeleteMealUseCase {
    private final MealRepository mealRepository;

    public DeleteMealUseCase(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public void execute(UUID id) {
        mealRepository.delete(id);
    }
}
