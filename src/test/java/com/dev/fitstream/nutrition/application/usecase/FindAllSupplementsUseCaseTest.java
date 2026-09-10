package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.model.Supplement;
import com.dev.fitstream.nutrition.domain.repository.SupplementRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FindAllSupplementsUseCaseTest {

    @Test
    @DisplayName("Should return all supplements")
    void shouldReturnAllSupplements() {
        var repository = mock(SupplementRepository.class);
        var useCase = new FindAllSupplementsUseCase(repository);
        when(repository.findAll()).thenReturn(List.of(
            new Supplement(null, "Whey", 30, "g", LocalDateTime.now())
        ));

        var result = useCase.execute();

        assertEquals(1, result.size());
        assertEquals("Whey", result.get(0).getName());
    }

}

