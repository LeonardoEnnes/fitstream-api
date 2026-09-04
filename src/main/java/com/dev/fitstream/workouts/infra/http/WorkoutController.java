package com.dev.fitstream.workouts.infra.http;

import com.dev.fitstream.workouts.application.usecase.FindWorkoutByIdUseCase;
import com.dev.fitstream.workouts.application.usecase.CreateWorkoutUseCase;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.UUID;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final CreateWorkoutUseCase createWorkoutUseCase;
    private final FindWorkoutByIdUseCase findWorkoutByIdUseCase;

    public WorkoutController(CreateWorkoutUseCase createWorkoutUseCase,  FindWorkoutByIdUseCase findWorkoutByIdUseCase) {
        this.createWorkoutUseCase = createWorkoutUseCase;
        this.findWorkoutByIdUseCase = findWorkoutByIdUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateWorkoutUseCase.Output> createWorkout (
        @RequestBody CreateWorkoutUseCase.Input input,
        @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey // faz sentido?
    ){
        CreateWorkoutUseCase.Output output = createWorkoutUseCase.execute(input, idempotencyKey);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindWorkoutByIdUseCase.Output> findWorkoutById (@PathVariable UUID id){
        FindWorkoutByIdUseCase.Output output = findWorkoutByIdUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(output);
    }
}
