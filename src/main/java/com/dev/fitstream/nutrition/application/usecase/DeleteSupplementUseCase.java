package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.repository.SupplementRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class DeleteSupplementUseCase {
    private final SupplementRepository repository;

    public DeleteSupplementUseCase(SupplementRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id) {
        repository.deleteById(id);
    }
}
