package com.dev.fitstream.workouts.infra.http;

import com.dev.fitstream.workouts.application.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workouts")
@Tag(name = "Workouts", description = "Endpoints para gerenciamento do catálogo de treinos")
public class WorkoutController {

    private final CreateWorkoutUseCase createWorkoutUseCase;
    private final ListWorkoutsUseCase listWorkoutsUseCase;
    private final FindWorkoutByIdUseCase findWorkoutByIdUseCase;
    private final UpdateWorkoutUseCase updateWorkoutUseCase;
    private final CompleteWorkoutUseCase completeWorkoutUseCase;
    private final DeleteWorkoutUseCase deleteWorkoutUseCase;

    public WorkoutController(
        CreateWorkoutUseCase createWorkoutUseCase,
        ListWorkoutsUseCase listWorkoutsUseCase,
        FindWorkoutByIdUseCase findWorkoutByIdUseCase,
        UpdateWorkoutUseCase updateWorkoutUseCase,
        CompleteWorkoutUseCase completeWorkoutUseCase,
        DeleteWorkoutUseCase deleteWorkoutUseCase
    ) {
        this.createWorkoutUseCase = createWorkoutUseCase;
        this.listWorkoutsUseCase = listWorkoutsUseCase;
        this.findWorkoutByIdUseCase = findWorkoutByIdUseCase;
        this.updateWorkoutUseCase = updateWorkoutUseCase;
        this.completeWorkoutUseCase = completeWorkoutUseCase;
        this.deleteWorkoutUseCase = deleteWorkoutUseCase;
    }

    @GetMapping
    @Operation(summary = "Lista todos os treinos", description = "Retorna o histórico de exercícios cadastrados.")
    public ResponseEntity<List<ListWorkoutsUseCase.Output>> listWorkouts() {
        return ResponseEntity.ok(listWorkoutsUseCase.execute());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um treino pelo ID", description = "Retorna os detalhes de um exercício específico.")
    public ResponseEntity<FindWorkoutByIdUseCase.Output> findWorkoutById(@PathVariable UUID id) {
        FindWorkoutByIdUseCase.Output output = findWorkoutByIdUseCase.execute(id);
        return ResponseEntity.ok(output);
    }

    @PostMapping
    @Operation(summary = "Cria um novo treino")
    public ResponseEntity<CreateWorkoutUseCase.Output> createWorkout (
        @RequestBody CreateWorkoutUseCase.Input input,
        @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey
    ){
        CreateWorkoutUseCase.Output output = createWorkoutUseCase.execute(input, idempotencyKey);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um treino existente")
    public ResponseEntity<UpdateWorkoutUseCase.Output> updateWorkout (
        @PathVariable UUID id,
        @RequestBody UpdateWorkoutUseCase.Input input
    ){
        UpdateWorkoutUseCase.Output output = updateWorkoutUseCase.execute(id, input);
        return ResponseEntity.ok(output);
    }

    @PatchMapping("/{id}/complete")
    @Operation(summary = "Conclui um treino", description = "Marca o status do exercício como concluído.")
    public ResponseEntity<CompleteWorkoutUseCase.Output> completeWorkout(@PathVariable UUID id) {
        var output = completeWorkoutUseCase.execute(id);
        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um treino")
    public ResponseEntity<Void> deleteWorkout(@PathVariable UUID id) {
        deleteWorkoutUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
