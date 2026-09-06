package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.exception.ResourceNotFoundException;
import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import com.dev.fitstream.workouts.domain.model.Workout;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UpdateWorkoutUseCase {

    private final WorkoutRepository  workoutRepository;

    public UpdateWorkoutUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Schema(description = "Dados para atualização do treino")
    public record Input(
        @Schema(description = "Novo título do treino", example = "Treino B - Inferiores (Atualizado)")
        String title,

        @Schema(description = "Nova descrição", example = "Agachamento livre 4x10, Leg Press 4x12, Cadeira Extensora 3x15")
        String description
    ) {}

    @Schema(description = "Treino atualizado retornado pelo sistema")
    public record Output(
        @Schema(description = "ID único do treino", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,

        @Schema(description = "Título do treino", example = "Treino B - Inferiores (Atualizado)")
        String title,

        @Schema(description = "Descrição detalhada", example = "Agachamento livre 4x10, Leg Press 4x12, Cadeira Extensora 3x15")
        String description,

        @Schema(description = "Indica se o treino foi concluído", example = "false")
        boolean completed
    ) {}

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
