package com.dev.fitstream.workouts.domain.repository;

import com.dev.fitstream.workouts.domain.model.Workout;
import java.util.Optional;
import java.util.UUID;

public interface WorkoutRepository {
    Workout save(Workout workout);
    Optional<Workout> findById(UUID id);
}
