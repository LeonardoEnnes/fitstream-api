package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.exception.ResourceNotFoundException;
import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import com.dev.fitstream.workouts.domain.model.Workout;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UpdateWorkoutUseCase {

    private final WorkoutRepository  workoutRepository;

    public UpdateWorkoutUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public record Input(String title, String description) {}
    public record Output(String id, String title, String description, boolean completed) {}

    public Output execute(UUID id, Input input) {
        Workout workout = workoutRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado com o ID: " + id));

        workout.updateDetails(input.title(), input.description());
        Workout updatedWorkout = workoutRepository.save(workout);

        return new Output(
            updatedWorkout.getId().toString(),
            updatedWorkout.getTitle(),
            updatedWorkout.getDescription(),
            updatedWorkout.isCompleted()
        );
    }
}
