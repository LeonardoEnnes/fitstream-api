package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.repository.SupplementRepository;
import com.dev.fitstream.nutrition.domain.model.Supplement;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FindAllSupplementsUseCase {
    private final SupplementRepository repository;

    public FindAllSupplementsUseCase(SupplementRepository repository) {
        this.repository = repository;
    }

    public List<Supplement> execute() {
        return repository.findAll();
    }
}
