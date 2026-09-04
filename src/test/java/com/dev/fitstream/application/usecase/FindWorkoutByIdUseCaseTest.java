package com.dev.fitstream.application.usecase;

import com.dev.fitstream.workouts.application.usecase.FindWorkoutByIdUseCase;
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

public class FindWorkoutByIdUseCaseTest {

    private WorkoutRepository workoutRepository;
    private FindWorkoutByIdUseCase findWorkoutByIdUseCase;

    @BeforeEach
    void setUp() {
        workoutRepository = mock(WorkoutRepository.class);
        findWorkoutByIdUseCase = new FindWorkoutByIdUseCase(workoutRepository);
    }

    @Test
    @DisplayName("Should return workout id successfully when id exists")
    void shouldFindWorkoutByIdSuccessfully() {
        UUID workoutId = UUID.randomUUID();
        Workout workout = new Workout(
            workoutId,
            "Crossfit",
            "Alguma descricao",
            false,
            LocalDateTime.now()
        );

        when(workoutRepository.findById(workoutId))
            .thenReturn(Optional.of(workout));

        var output = findWorkoutByIdUseCase.execute(workoutId);

        assertNotNull(output);
        assertEquals(workoutId.toString(), output.id());
        assertEquals("Crossfit", output.title());
        assertEquals("Alguma descricao", output.description());
        assertFalse(output.completed());

        verify(workoutRepository, times(1)).findById(workoutId);
    }

    @Test
    @DisplayName("Should return an exception when workout is not found by ID")
    void shouldThrowExceptionWhenWorkoutIsNotFound() {
        UUID workoutId = UUID.randomUUID();
        when(workoutRepository.findById(workoutId))
            .thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> findWorkoutByIdUseCase.execute(workoutId));

        assertEquals("Treino não encontrado com o ID: " + workoutId, exception.getMessage());
        verify(workoutRepository, times(1)).findById(workoutId);
    }
}
