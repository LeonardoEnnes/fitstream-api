package com.dev.fitstream.workouts.infra.http;

import com.dev.fitstream.workouts.application.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.UUID;

@RestController
@RequestMapping("/workouts")
@Tag(name = "Workouts", description = "Endpoints para gerenciamento do catálogo de treinos")
public class WorkoutController {

    private final CreateWorkoutUseCase createWorkoutUseCase;
    private final FindWorkoutByIdUseCase findWorkoutByIdUseCase;
    private final UpdateWorkoutUseCase updateWorkoutUseCase;
    private final CompleteWorkoutUseCase completeWorkoutUseCase;
    private final DeleteWorkoutUseCase deleteWorkoutUseCase;

    public WorkoutController(
        CreateWorkoutUseCase createWorkoutUseCase, 
        CompleteWorkoutUseCase completeWorkoutUseCase, 
        FindWorkoutByIdUseCase findWorkoutByIdUseCase,   
        UpdateWorkoutUseCase updateWorkoutUseCase,
        DeleteWorkoutUseCase deleteWorkoutUseCase
    ) {
        this.createWorkoutUseCase = createWorkoutUseCase;
        this.findWorkoutByIdUseCase = findWorkoutByIdUseCase;
        this.updateWorkoutUseCase = updateWorkoutUseCase;
        this.completeWorkoutUseCase = completeWorkoutUseCase;
        this.deleteWorkoutUseCase = deleteWorkoutUseCase;
    }

    @PostMapping
    @Operation(summary = "Cria um novo treino", description = "Registra um novo treino no sistema. O status inicial será sempre não-concluído.")
    public ResponseEntity<CreateWorkoutUseCase.Output> createWorkout (
        @RequestBody CreateWorkoutUseCase.Input input,
        @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey
    ){
        CreateWorkoutUseCase.Output output = createWorkoutUseCase.execute(input, idempotencyKey);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping("/{id}")
    // isso n apareceu
    @Operation(summary = "Busca um treino pelo ID", description = "Retorna os detalhes de um treino específico baseado no seu identificador único (UUID).")
    public ResponseEntity<FindWorkoutByIdUseCase.Output> findWorkoutById (@PathVariable UUID id){
        FindWorkoutByIdUseCase.Output output = findWorkoutByIdUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(output);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um treino existente", description = "Substitui os dados de título e descrição de um treino previamente cadastrado.")
    public ResponseEntity<UpdateWorkoutUseCase.Output> updateWorkout (
        @PathVariable UUID id,
        @RequestBody UpdateWorkoutUseCase.Input input
    ){
        UpdateWorkoutUseCase.Output output = updateWorkoutUseCase.execute(id, input);
        return ResponseEntity.ok(output);
    }

    @PatchMapping("/{id}/complete")
    @Operation(summary = "Conclui um treino", description = "Marca o status do treino apontado pelo ID como concluído (completed = true).")
    public ResponseEntity<CompleteWorkoutUseCase.Output> completeWorkout(@PathVariable UUID id) {
        var output = completeWorkoutUseCase.execute(id);
        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um treino", description = "Deleta fisicamente um treino do banco de dados pelo seu identificador único.")
    public ResponseEntity<Void> deleteWorkout(@PathVariable UUID id) {
        deleteWorkoutUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}