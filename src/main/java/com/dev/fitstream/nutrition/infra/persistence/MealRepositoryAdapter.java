package com.dev.fitstream.nutrition.infra.persistence;

import com.dev.fitstream.nutrition.domain.model.Meal;
import com.dev.fitstream.nutrition.domain.repository.MealRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MealRepositoryAdapter implements MealRepository {

    private final SpringDataMealRepository repository;

    public MealRepositoryAdapter(SpringDataMealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal) {
        MealEntity entity = new MealEntity(
            meal.getId(),
            meal.getName(),
            meal.getCalories(),
            meal.getProtein(),
            meal.getCarbs(),
            meal.getFat(),
            meal.getConsumedAt()
        );
        MealEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Meal> findAll() {
        return repository.findAll().stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<Meal> findByConsumedAtBetween(LocalDateTime start, LocalDateTime end) {
        return repository.findByConsumedAtBetween(start, end).stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }

    private Meal toDomain(MealEntity entity) {
        return new Meal(
            entity.getId(),
            entity.getName(),
            entity.getCalories(),
            entity.getProtein(),
            entity.getCarbs(),
            entity.getFat(),
            entity.getConsumedAt()
        );
    }
}
