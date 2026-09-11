package com.dev.fitstream.nutrition.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SpringDataMealRepository extends JpaRepository<MealEntity, UUID> {
    List<MealEntity> findByConsumedAtBetween(LocalDateTime start, LocalDateTime end);
}
