package com.dev.fitstream.wokouts.domain.repository;

import com.dev.fitstream.wokouts.domain.model.Workout;
import java.util.Optional;
import java.util.UUID;

public interface WorkoutRepository {
    Workout save(Workout workout);
    Optional<Workout> findById(UUID id);
}
