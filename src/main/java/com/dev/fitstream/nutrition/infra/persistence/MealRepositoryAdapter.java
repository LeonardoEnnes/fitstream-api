package com.dev.fitstream.nutrition.infra.persistence;

import com.dev.fitstream.nutrition.domain.model.Meal;
import com.dev.fitstream.nutrition.domain.repository.MealRepository;
import org.springframework.stereotype.Component;

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
            meal.getDescription(),
            meal.getConsumedAt()
        );
        MealEntity saved = repository.save(entity);
        return new Meal(saved.getId(), saved.getName(), saved.getDescription(), saved.getConsumedAt());
    }

    @Override
    public List<Meal> findAll() {
        return repository.findAll().stream()
            .map(e -> new Meal(e.getId(), e.getName(), e.getDescription(), e.getConsumedAt()))
            .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
