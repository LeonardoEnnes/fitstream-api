package com.dev.fitstream.workouts.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataWorkoutRepository extends JpaRepository<WorkoutEntity, UUID> {
}
