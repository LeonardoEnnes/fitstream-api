package com.dev.fitstream.nutrition.infra.http;

import com.dev.fitstream.nutrition.application.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/supplements")
@Tag(name = "Nutrition")
public class SupplementController {

    private final CreateSupplementUseCase createSupplementUseCase;
    private final FindAllSupplementsUseCase findAllSupplementsUseCase;
    private final DeleteSupplementUseCase deleteSupplementUseCase;

    public SupplementController(
        CreateSupplementUseCase createSupplementUseCase,
        FindAllSupplementsUseCase findAllSupplementsUseCase,
        DeleteSupplementUseCase deleteSupplementUseCase
    ) {
        this.createSupplementUseCase = createSupplementUseCase;
        this.findAllSupplementsUseCase = findAllSupplementsUseCase;
        this.deleteSupplementUseCase = deleteSupplementUseCase;
    }

    @PostMapping
    @Operation(summary = "Registra um suplemento", description = "Adiciona um novo suplemento ao consumo diário.")
    public ResponseEntity<CreateSupplementUseCase.Output> create(@RequestBody CreateSupplementUseCase.Input input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createSupplementUseCase.execute(input));
    }

    @GetMapping
    @Operation(summary = "Lista suplementos", description = "Retorna o histórico de suplementação.")
    public ResponseEntity<List<CreateSupplementUseCase.Output>> findAll() {
        List<CreateSupplementUseCase.Output> response = findAllSupplementsUseCase.execute().stream()
            .map(s -> new CreateSupplementUseCase.Output(
                s.getId().toString(), s.getName(), s.getDosage(), s.getUnit(), s.getTakenAt().toString()
            )).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um suplemento", description = "Deleta o registro pelo ID.")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteSupplementUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
