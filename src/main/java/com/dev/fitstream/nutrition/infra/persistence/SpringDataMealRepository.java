package com.dev.fitstream.nutrition.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataMealRepository extends JpaRepository<MealEntity, UUID> {}
