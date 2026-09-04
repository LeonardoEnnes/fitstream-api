package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import com.dev.fitstream.workouts.domain.model.Workout;
import org.springframework.stereotype.Service;

@Service
public class CreateWorkoutUseCase {
    private WorkoutRepository workoutRepository;

    public CreateWorkoutUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public record Input(String title, String description){}
    public record Output(String id, String title, String description, boolean completed) {}

    public Output execute(Input input, String idempotencyKey) {
        // Idempotência

        Workout workout = new Workout(null, input.title(), input.description(), false, null);
        Workout savedWorkout = workoutRepository.save(workout);

        return new Output(
            savedWorkout.getId().toString(),
            savedWorkout.getTitle(),
            savedWorkout.getDescription(),
            savedWorkout.isCompleted()
        );
    }
}
