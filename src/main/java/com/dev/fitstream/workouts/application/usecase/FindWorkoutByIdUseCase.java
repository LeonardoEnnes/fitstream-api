package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.exception.ResourceNotFoundException;
import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import com.dev.fitstream.workouts.domain.model.Workout;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class FindWorkoutByIdUseCase {
    private final WorkoutRepository workoutRepository;

    public FindWorkoutByIdUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    // isso serve para retornar os dados do treino de forma estruturada, sem expor a entidade diretamente
    public record Output(String id, String title, String description, boolean completed, String createdAt) {}

    public Output execute(UUID id) {
        Workout workout = workoutRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado com o ID: " + id));
            
        return new Output(
            workout.getId().toString(),
            workout.getTitle(),
            workout.getDescription(),
            workout.isCompleted(),
            workout.getCreatedAt().toString()
        );
    }
}
