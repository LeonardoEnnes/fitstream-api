package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import com.dev.fitstream.workouts.domain.model.Workout;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Service;

@Service
public class CreateWorkoutUseCase {
    private final WorkoutRepository workoutRepository;

    public CreateWorkoutUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Schema(description = "Dados de entrada para criar um novo exercício de musculação")
    public record Input(
        @Schema(description = "Nome do exercício", example = "Supino Reto")
        String exercise,

        @Schema(description = "Número de séries", example = "4")
        int sets,

        @Schema(description = "Número de repetições", example = "10")
        int reps,

        @Schema(description = "Carga utilizada em kg", example = "80.0")
        double weight
    ) {}

    @Schema(description = "Exercício criado com sucesso")
    public record Output(
        @Schema(description = "ID único do treino", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,

        @Schema(description = "Nome do exercício", example = "Supino Reto")
        String exercise,

        @Schema(description = "Número de séries", example = "4")
        int sets,

        @Schema(description = "Número de repetições", example = "10")
        int reps,

        @Schema(description = "Carga utilizada em kg", example = "80.0")
        double weight,

        @Schema(description = "Status de conclusão", example = "false")
        boolean completed
    ) {}

    public Output execute(Input input, String idempotencyKey) {
        Workout workout = new Workout(null, input.exercise(), input.sets(), input.reps(), input.weight(), false, null);
        Workout saved = workoutRepository.save(workout);

        return new Output(
            saved.getId().toString(),
            saved.getExercise(),
            saved.getSets(),
            saved.getReps(),
            saved.getWeight(),
            saved.isCompleted()
        );
    }
}
