package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.exception.ResourceNotFoundException;
import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import com.dev.fitstream.shared.application.port.out.EventPublisher;
import com.dev.fitstream.workouts.domain.model.Workout;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

class CompleteWorkoutUseCaseTest {

    private WorkoutRepository workoutRepositoryMock;
    private EventPublisher eventPublisherMock;
    private CompleteWorkoutUseCase completeWorkoutUseCase;

    @BeforeEach
    void setUp() {
        workoutRepositoryMock = mock(WorkoutRepository.class);
        eventPublisherMock = mock(EventPublisher.class);
        // Ordem exata do construtor: EventPublisher, WorkoutRepository
        completeWorkoutUseCase = new CompleteWorkoutUseCase(eventPublisherMock, workoutRepositoryMock);
    }

    @Test
    @DisplayName("Should set workout as completed with success")
    void shouldCompleteWorkoutSuccessfully() {
        UUID workoutId = UUID.randomUUID();
        Workout workout = new Workout(workoutId, "Supino Reto", 4, 10, 80.0, false, LocalDateTime.now());

        when(workoutRepositoryMock.findById(workoutId)).thenReturn(Optional.of(workout));
        when(workoutRepositoryMock.save(any(Workout.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var output = completeWorkoutUseCase.execute(workoutId);

        assertNotNull(output);
        assertEquals(workoutId.toString(), output.id());
        assertTrue(output.completed());

        verify(workoutRepositoryMock, times(1)).findById(workoutId);
        verify(workoutRepositoryMock, times(1)).save(any(Workout.class));
        verify(eventPublisherMock, times(1)).publishLiveFeedEvent(eq("TREINO"), any(String.class));
    }

    @Test
    @DisplayName("Should Throw ResourceNotFoundException when trying to complete Nonexistent workout")
    void shouldThrowExceptionWhenWorkoutNotFoundToComplete() {
        UUID workoutId = UUID.randomUUID();
        when(workoutRepositoryMock.findById(workoutId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            completeWorkoutUseCase.execute(workoutId);
        });

        verify(workoutRepositoryMock, times(1)).findById(workoutId);
        verify(workoutRepositoryMock, never()).save(any(Workout.class));
        verify(eventPublisherMock, never()).publishLiveFeedEvent(any(), any());
    }
}
