package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import com.dev.fitstream.workouts.domain.model.Workout;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Service;
@Service
public class CreateWorkoutUseCase {
    private WorkoutRepository workoutRepository;

    public CreateWorkoutUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Schema(description = "Dados de entrada para criar um novo treino")
    public record Input(
        @Schema(description = "Título do treino", example = "Treino A - Superiores")
        String title,

        @Schema(description = "Descrição detalhada dos exercícios", example = "Supino reto 4x10, Crucifixo 3x12")
        String description
    ) {}
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
