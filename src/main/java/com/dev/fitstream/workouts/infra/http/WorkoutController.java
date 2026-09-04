package com.dev.fitstream.workouts.infra.http;

import com.dev.fitstream.workouts.application.usecase.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.UUID;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final CreateWorkoutUseCase createWorkoutUseCase; // final serve para
    private final FindWorkoutByIdUseCase findWorkoutByIdUseCase;
    private final UpdateWorkoutUseCase updateWorkoutUseCase;
    private final CompleteWorkoutUseCase completeWorkoutUseCase;
    private final DeleteWorkoutUseCase deleteWorkoutUseCase;

    public WorkoutController(CreateWorkoutUseCase createWorkoutUseCase, CompleteWorkoutUseCase completeWorkoutUseCase, FindWorkoutByIdUseCase findWorkoutByIdUseCase,   UpdateWorkoutUseCase updateWorkoutUseCase,
                             DeleteWorkoutUseCase  deleteWorkoutUseCase
    ) {
        this.createWorkoutUseCase = createWorkoutUseCase;
        this.findWorkoutByIdUseCase = findWorkoutByIdUseCase;
        this.updateWorkoutUseCase = updateWorkoutUseCase;
        this.completeWorkoutUseCase = completeWorkoutUseCase;
        this.deleteWorkoutUseCase = deleteWorkoutUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateWorkoutUseCase.Output> createWorkout (
        @RequestBody CreateWorkoutUseCase.Input input,
        @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey
    ){
        CreateWorkoutUseCase.Output output = createWorkoutUseCase.execute(input, idempotencyKey);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindWorkoutByIdUseCase.Output> findWorkoutById (@PathVariable UUID id){
        FindWorkoutByIdUseCase.Output output = findWorkoutByIdUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(output);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateWorkoutUseCase.Output> updateWorkout (
        @PathVariable UUID id,
        @RequestBody UpdateWorkoutUseCase.Input input
    ){
        UpdateWorkoutUseCase.Output output = updateWorkoutUseCase.execute(id, input);
        return ResponseEntity.ok(output);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<CompleteWorkoutUseCase.Output> completeWorkout(@PathVariable UUID id) {
        var output = completeWorkoutUseCase.execute(id);
        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable UUID id) {
        deleteWorkoutUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
