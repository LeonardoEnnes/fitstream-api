package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.model.Meal;
import com.dev.fitstream.nutrition.domain.repository.MealRepository;
import com.dev.fitstream.shared.application.port.out.EventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CreateMealUseCaseTest {

    private MealRepository mealRepositoryMock;
    private EventPublisher eventPublisherMock;
    private CreateMealUseCase createMealUseCase;

    @BeforeEach
    void setUp() {
        mealRepositoryMock = mock(MealRepository.class);
        eventPublisherMock = mock(EventPublisher.class);
        createMealUseCase = new CreateMealUseCase(mealRepositoryMock, eventPublisherMock);
    }

    @Test
    @DisplayName("Deve criar uma refeição com sucesso, salvar e disparar evento")
    void shouldCreateMealSuccessfully() {
        var input = new CreateMealUseCase.Input("Almoço", 500, 40, 60, 15);
        when(mealRepositoryMock.save(any(Meal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var output = createMealUseCase.execute(input);

        assertNotNull(output);
        assertNotNull(output.id());
        assertEquals("Almoço", output.name());
        assertEquals(500, output.calories());
        assertEquals(40, output.protein());
        assertNotNull(output.consumedAt());

        verify(mealRepositoryMock, times(1)).save(any(Meal.class));
        verify(eventPublisherMock, times(1)).publishLiveFeedEvent(eq("NUTRITION"), any(String.class));
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando o nome da refeição for vazio e não disparar evento")
    void shouldThrowExceptionWhenMealNameIsBlank() {
        var input = new CreateMealUseCase.Input("", 500, 40, 60, 15);

        assertThrows(IllegalArgumentException.class, () -> {
            createMealUseCase.execute(input);
        });

        verify(mealRepositoryMock, never()).save(any(Meal.class));
        verify(eventPublisherMock, never()).publishLiveFeedEvent(any(), any());
    }
}
