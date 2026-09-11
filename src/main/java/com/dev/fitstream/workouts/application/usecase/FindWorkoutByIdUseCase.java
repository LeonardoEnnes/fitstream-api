package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.exception.ResourceNotFoundException;
import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import com.dev.fitstream.workouts.domain.model.Workout;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class FindWorkoutByIdUseCase {
    private final WorkoutRepository workoutRepository;

    public FindWorkoutByIdUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Schema(description = "Detalhes completos do exercício encontrado")
    public record Output(
        @Schema(description = "ID único do treino", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,

        @Schema(description = "Nome do exercício", example = "Supino Reto")
        String exercise,

        @Schema(description = "Número de séries", example = "4")
        int sets,

        @Schema(description = "Número de repetições", example = "10")
        int reps,

        @Schema(description = "Carga utilizada", example = "80.0")
        double weight,

        @Schema(description = "Indica se o treino foi concluído", example = "true")
        boolean completed,

        @Schema(description = "Data e hora em que foi criado", example = "2026-09-04T15:30:00")
        String createdAt
    ) {}

    public Output execute(UUID id) {
        Workout workout = workoutRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado com o ID: " + id));

        return new Output(
            workout.getId().toString(),
            workout.getExercise(),
            workout.getSets(),
            workout.getReps(),
            workout.getWeight(),
            workout.isCompleted(),
            workout.getCreatedAt().toString()
        );
    }
}
