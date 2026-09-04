package com.dev.fitstream.workouts.infra.persistence;

import com.dev.fitstream.workouts.domain.model.Workout;
import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

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
            workout.getTitle(),
            workout.getDescription(),
            workout.isCompleted(),
            workout.getCreatedAt()
        );

        WorkoutEntity savedEntity = springDataRepository.save(entity);

        return new Workout(
            savedEntity.getId(),
            savedEntity.getTitle(),
            savedEntity.getDescription(),
            savedEntity.isCompleted(),
            savedEntity.getCreatedAt()
        );
    }

    @Override
    public Optional<Workout> findById(UUID id) {
        return springDataRepository.findById(id).map(entity -> new Workout(
            entity.getId(),
            entity.getTitle(),
            entity.getDescription(),
            entity.isCompleted(),
            entity.getCreatedAt()
        ));
    }

    @Override
    public void delete(UUID id) {
        springDataRepository.deleteById(id);
    }
}
