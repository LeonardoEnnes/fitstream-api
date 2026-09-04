package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.exception.ResourceNotFoundException;
import com.dev.fitstream.workouts.domain.model.Workout;
import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CompleteWorkoutUseCase {
    private final WorkoutRepository workoutRepository;

    public CompleteWorkoutUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public record Output(String id, boolean completed) {}

    public Output execute(UUID id) {
        Workout workout = workoutRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado com o ID: " + id));

        workout.complete();
        Workout saved = workoutRepository.save(workout);

        return new Output(saved.getId().toString(), saved.isCompleted());
    }
}
