package com.dev.fitstream.application.usecase;

import com.dev.fitstream.workouts.application.usecase.CreateWorkoutUseCase;
import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import com.dev.fitstream.workouts.domain.model.Workout;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class CreateWorkoutUseCaseTest {

    private WorkoutRepository workoutRepository;
    private CreateWorkoutUseCase createWorkoutUseCase;

    @BeforeEach
    public void setup() { // crindo mock
        workoutRepository = mock(WorkoutRepository.class);
        createWorkoutUseCase = new CreateWorkoutUseCase(workoutRepository);
    }

    @Test
    @DisplayName("Should create an workout with success when data is valid")
    void shouldCreatWorkoutWithSuccessWhenDataIsValid() {
        // Arrange
        var input = new CreateWorkoutUseCase.Input("Treino de Força", "Pernas e Core");

        when(workoutRepository.save(any(Workout.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var output = createWorkoutUseCase.execute(input, "some-idempotency-key");

        assertNotNull(output);
        assertEquals("Treino de Força", output.title());
        assertEquals("Pernas e Core", output.description());
        assertFalse(output.completed());
        assertNotNull(output.id());

        verify(workoutRepository, times(1)).save(any(Workout.class));
    }
}
