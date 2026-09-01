package com.dev.fitstream.wokouts.infra.http;

import com.dev.fitstream.wokouts.application.usecase.CreateWorkoutUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final CreateWorkoutUseCase createWorkoutUseCase;

    public WorkoutController(CreateWorkoutUseCase createWorkoutUseCase) {
        this.createWorkoutUseCase = createWorkoutUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateWorkoutUseCase.Output> createWorkout (
        @RequestBody CreateWorkoutUseCase.Input input,
        @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey // faz sentido?
    ){
        CreateWorkoutUseCase.Output output = createWorkoutUseCase.execute(input, idempotencyKey);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }
}
