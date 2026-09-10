package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.repository.SupplementRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.mockito.Mockito.*;

class DeleteSupplementUseCaseTest {

    @Test
    @DisplayName ("Should delete a supplement successfully by ID")  
    void shouldDeleteSupplement() {
        var repository = mock(SupplementRepository.class);
        var useCase = new DeleteSupplementUseCase(repository);
        UUID id = UUID.randomUUID();

        useCase.execute(id);

        verify(repository, times(1)).deleteById(id);
    }

}
