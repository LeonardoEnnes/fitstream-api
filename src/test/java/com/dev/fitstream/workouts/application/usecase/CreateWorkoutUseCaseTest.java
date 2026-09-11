package com.dev.fitstream.workouts.application.usecase;

import com.dev.fitstream.workouts.domain.repository.WorkoutRepository;
import com.dev.fitstream.workouts.domain.model.Workout;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

public class CreateWorkoutUseCaseTest {

    private WorkoutRepository workoutRepository;
    private CreateWorkoutUseCase createWorkoutUseCase;

    @BeforeEach
    public void setup() {
        workoutRepository = mock(WorkoutRepository.class);
        createWorkoutUseCase = new CreateWorkoutUseCase(workoutRepository);
    }

    @Test
    @DisplayName("Should create an workout with success when data is valid")
    void shouldCreatWorkoutWithSuccessWhenDataIsValid() {
        var input = new CreateWorkoutUseCase.Input("Supino Reto", 4, 10, 80.0);

        when(workoutRepository.save(any(Workout.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var output = createWorkoutUseCase.execute(input, "some-idempotency-key");

        assertNotNull(output);
        assertEquals("Supino Reto", output.exercise());
        assertEquals(4, output.sets());
        assertEquals(10, output.reps());
        assertEquals(80.0, output.weight());
        assertFalse(output.completed());
        assertNotNull(output.id());

        verify(workoutRepository, times(1)).save(any(Workout.class));
    }
}
