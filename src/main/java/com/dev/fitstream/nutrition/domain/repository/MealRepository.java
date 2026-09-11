package com.dev.fitstream.nutrition.domain.repository;

import com.dev.fitstream.nutrition.domain.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MealRepository {
    Meal save(Meal meal);
    List<Meal> findAll();
    void delete(UUID id);
    List<Meal> findByConsumedAtBetween(LocalDateTime start, LocalDateTime end);
}
