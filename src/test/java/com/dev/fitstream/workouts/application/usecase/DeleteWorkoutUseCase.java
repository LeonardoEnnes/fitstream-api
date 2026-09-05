package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.exception.ResourceNotFoundException;
import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import com.dev.fitstream.workouts.domain.model.Workout;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

class DeleteWorkoutUseCaseTest {

    private WorkoutRepository workoutRepositoryMock;
    private DeleteWorkoutUseCase deleteWorkoutUseCase;

    @BeforeEach
    void setUp() {
        workoutRepositoryMock = mock(WorkoutRepository.class);
        deleteWorkoutUseCase = new DeleteWorkoutUseCase(workoutRepositoryMock);
    }

    @Test
    @DisplayName("Should delete workout successfully")
    void shouldDeleteWorkoutSuccessfully() {
        UUID workoutId = UUID.randomUUID();
        Workout workout = new Workout(workoutId, "Cardio", "Corrida", false, LocalDateTime.now());

        when(workoutRepositoryMock.findById(workoutId)).thenReturn(Optional.of(workout));
        doNothing().when(workoutRepositoryMock).delete(workoutId);

        assertDoesNotThrow(() -> deleteWorkoutUseCase.execute(workoutId));

        verify(workoutRepositoryMock, times(1)).findById(workoutId);
        verify(workoutRepositoryMock, times(1)).delete(workoutId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when workout is not found")
    void shouldThrowExceptionWhenWorkoutNotFoundToDelete() {
        UUID workoutId = UUID.randomUUID();
        when(workoutRepositoryMock.findById(workoutId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            deleteWorkoutUseCase.execute(workoutId);
        });

        verify(workoutRepositoryMock, times(1)).findById(workoutId);
        verify(workoutRepositoryMock, never()).delete(any());
    }
}
