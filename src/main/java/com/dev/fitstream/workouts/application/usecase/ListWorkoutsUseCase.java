package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListWorkoutsUseCase {
    private final WorkoutRepository workoutRepository;

    public ListWorkoutsUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public record Output(
        String id,
        String exercise,
        int sets,
        int reps,
        double weight,
        boolean completed
    ) {}

    public List<Output> execute() {
        return workoutRepository.findAll().stream()
            .map(w -> new Output(
                w.getId().toString(),
                w.getExercise(),
                w.getSets(),
                w.getReps(),
                w.getWeight(),
                w.isCompleted()
            ))
            .collect(Collectors.toList());
    }
}
