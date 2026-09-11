package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.exception.ResourceNotFoundException;
import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import com.dev.fitstream.workouts.domain.model.Workout;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

class UpdateWorkoutUseCaseTest {

    private WorkoutRepository workoutRepositoryMock;
    private UpdateWorkoutUseCase updateWorkoutUseCase;

    @BeforeEach
    void setUp() {
        workoutRepositoryMock = mock(WorkoutRepository.class);
        updateWorkoutUseCase = new UpdateWorkoutUseCase(workoutRepositoryMock);
    }

    @Test
    @DisplayName("Should update workout details successfully")
    void shouldUpdateWorkoutSuccessfully() {
        UUID workoutId = UUID.randomUUID();
        Workout existingWorkout = new Workout(workoutId, "Supino Reto", 4, 10, 80.0, false, LocalDateTime.now());

        UpdateWorkoutUseCase.Input input = new UpdateWorkoutUseCase.Input("Supino Inclinado", 3, 12, 70.0);

        when(workoutRepositoryMock.findById(workoutId)).thenReturn(Optional.of(existingWorkout));
        when(workoutRepositoryMock.save(any(Workout.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var output = updateWorkoutUseCase.execute(workoutId, input);

        assertNotNull(output);
        assertEquals(workoutId.toString(), output.id());
        assertEquals("Supino Inclinado", output.exercise());
        assertEquals(3, output.sets());
        assertEquals(12, output.reps());
        assertEquals(70.0, output.weight());
        assertFalse(output.completed());

        verify(workoutRepositoryMock, times(1)).findById(workoutId);
        verify(workoutRepositoryMock, times(1)).save(any(Workout.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating nonexistent workout")
    void shouldThrowExceptionWhenWorkoutNotFoundToUpdate() {
        UUID workoutId = UUID.randomUUID();
        UpdateWorkoutUseCase.Input input = new UpdateWorkoutUseCase.Input("Supino Inclinado", 3, 12, 70.0);

        when(workoutRepositoryMock.findById(workoutId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            updateWorkoutUseCase.execute(workoutId, input);
        });

        verify(workoutRepositoryMock, times(1)).findById(workoutId);
        verify(workoutRepositoryMock, never()).save(any(Workout.class));
    }
}
