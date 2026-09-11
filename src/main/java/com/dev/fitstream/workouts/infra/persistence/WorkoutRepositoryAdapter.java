package com.dev.fitstream.workouts.infra.persistence;

import com.dev.fitstream.workouts.domain.model.Workout;
import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class WorkoutRepositoryAdapter implements WorkoutRepository {
    private final SpringDataWorkoutRepository springDataRepository;

    public WorkoutRepositoryAdapter(SpringDataWorkoutRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Workout save(Workout workout) {
        WorkoutEntity entity = new WorkoutEntity(
            workout.getId(),
            workout.getExercise(),
            workout.getSets(),
            workout.getReps(),
            workout.getWeight(),
            workout.isCompleted(),
            workout.getCreatedAt()
        );
        WorkoutEntity savedEntity = springDataRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Workout> findById(UUID id) {
        return springDataRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Workout> findAll() {
        return springDataRepository.findAll().stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        springDataRepository.deleteById(id);
    }

    private Workout toDomain(WorkoutEntity entity) {
        return new Workout(
            entity.getId(),
            entity.getExercise(),
            entity.getSets(),
            entity.getReps(),
            entity.getWeight(),
            entity.isCompleted(),
            entity.getCreatedAt()
        );
    }
}
