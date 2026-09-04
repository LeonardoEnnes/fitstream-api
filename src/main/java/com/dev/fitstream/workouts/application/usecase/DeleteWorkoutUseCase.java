package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.exception.ResourceNotFoundException;
import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class DeleteWorkoutUseCase {
    private final WorkoutRepository workoutRepository;

    public DeleteWorkoutUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public void execute(UUID id) {
        if (workoutRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Treino não encontrado com o ID: " + id);
        }
        workoutRepository.delete(id);
    }
}
