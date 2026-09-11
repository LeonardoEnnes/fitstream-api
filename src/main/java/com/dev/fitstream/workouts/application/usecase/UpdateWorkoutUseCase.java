package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.exception.ResourceNotFoundException;
import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import com.dev.fitstream.workouts.domain.model.Workout;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UpdateWorkoutUseCase {

    private final WorkoutRepository workoutRepository;

    public UpdateWorkoutUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Schema(description = "Dados para atualização do exercício")
    public record Input(
        @Schema(description = "Novo nome do exercício", example = "Supino Inclinado")
        String exercise,

        @Schema(description = "Novo número de séries", example = "3")
        int sets,

        @Schema(description = "Novo número de repetições", example = "12")
        int reps,

        @Schema(description = "Nova carga utilizada", example = "70.0")
        double weight
    ) {}

    @Schema(description = "Exercício atualizado retornado pelo sistema")
    public record Output(
        @Schema(description = "ID único do treino", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,

        @Schema(description = "Nome do exercício", example = "Supino Inclinado")
        String exercise,

        @Schema(description = "Séries", example = "3")
        int sets,

        @Schema(description = "Repetições", example = "12")
        int reps,

        @Schema(description = "Carga", example = "70.0")
        double weight,

        @Schema(description = "Indica se o treino foi concluído", example = "false")
        boolean completed
    ) {}

    public Output execute(UUID id, Input input) {
        Workout workout = workoutRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado com o ID: " + id));

        workout.updateDetails(input.exercise(), input.sets(), input.reps(), input.weight());
        Workout updatedWorkout = workoutRepository.save(workout);

        return new Output(
            updatedWorkout.getId().toString(),
            updatedWorkout.getExercise(),
            updatedWorkout.getSets(),
            updatedWorkout.getReps(),
            updatedWorkout.getWeight(),
            updatedWorkout.isCompleted()
        );
    }
}
