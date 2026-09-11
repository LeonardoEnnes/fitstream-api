package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.exception.ResourceNotFoundException;
import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import com.dev.fitstream.shared.application.port.out.EventPublisher;
import com.dev.fitstream.workouts.domain.model.Workout;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CompleteWorkoutUseCase {
    private final WorkoutRepository workoutRepository;
    private final EventPublisher eventPublisher;

    public CompleteWorkoutUseCase(EventPublisher eventPublisher, WorkoutRepository workoutRepository) {
        this.eventPublisher = eventPublisher;
        this.workoutRepository = workoutRepository;
    }

    @Schema(description = "Resultado da conclusão do treino")
    public record Output(
        @Schema(description = "ID único do treino concluído", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,

        @Schema(description = "Status final de conclusão sempre verdadeiro", example = "true")
        boolean completed
    ) {}

    public Output execute(UUID id) {
        Workout workout = workoutRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado com o ID: " + id));

        workout.complete();
        Workout saved = workoutRepository.save(workout);

        eventPublisher.publishLiveFeedEvent("TREINO", "Exercício concluído: " + saved.getExercise());

        return new Output(saved.getId().toString(), saved.isCompleted());
    }
}
