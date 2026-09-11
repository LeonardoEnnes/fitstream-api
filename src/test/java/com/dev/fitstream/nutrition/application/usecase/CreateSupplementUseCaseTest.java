package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.repository.SupplementRepository;
import com.dev.fitstream.shared.application.port.out.EventPublisher;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

class CreateSupplementUseCaseTest {
    private SupplementRepository repository;
    private EventPublisher eventPublisherMock;
    private CreateSupplementUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(SupplementRepository.class);
        eventPublisherMock = mock(EventPublisher.class);
        useCase = new CreateSupplementUseCase(repository, eventPublisherMock);
    }

    @Test
    @DisplayName("Should create a supplement successfully")
    void shouldCreateSupplement() {
        var input = new CreateSupplementUseCase.Input("Creatina", 5, "g");
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        var output = useCase.execute(input);

        assertNotNull(output.id());
        assertEquals("Creatina", output.name());
        verify(repository, times(1)).save(any());
        verify(eventPublisherMock, times(1)).publishLiveFeedEvent(eq("SUPLEMENTAÇÃO"), any(String.class));
    }

    @Test
    void shouldThrowExceptionWhenDosageIsInvalid() {
        var input = new CreateSupplementUseCase.Input("Creatina", 0, "g");
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(input));
        verify(eventPublisherMock, never()).publishLiveFeedEvent(any(), any());
    }
}
