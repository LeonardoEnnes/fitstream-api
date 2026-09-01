package com.dev.fitstream.application.usecase;

import com.dev.fitstream.wokouts.domain.model.Workout;
import com.dev.fitstream.wokouts.domain.repository.WorkoutRepository;
import org.junit.jupiter.api.BeforeEach;
import com.dev.fitstream.wokouts.application.usecase.CreateWorkoutUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

        // Act
        var output = createWorkoutUseCase.execute(input, "some-idempotency-key");

        // Assert
        assertNotNull(output);
        assertEquals("Treino de Força", output.title());
        assertEquals("Pernas e Core", output.description());
        assertFalse(output.completed());
        assertNotNull(output.id());

        // Garantimos que o repositório foi chamado exatamente uma vez
        verify(workoutRepository, times(1)).save(any(Workout.class));
    }
}
