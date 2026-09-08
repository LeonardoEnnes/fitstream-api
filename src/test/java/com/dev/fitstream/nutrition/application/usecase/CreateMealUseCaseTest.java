package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.repository.MealRepository;
import com.dev.fitstream.nutrition.domain.model.Meal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

class CreateMealUseCaseTest {

    private MealRepository mealRepositoryMock;
    private CreateMealUseCase createMealUseCase;

    @BeforeEach
    void setUp() {
        mealRepositoryMock = mock(MealRepository.class);
        createMealUseCase = new CreateMealUseCase(mealRepositoryMock);
    }

    @Test
    @DisplayName("Deve criar uma refeição com sucesso quando os dados forem válidos")
    void shouldCreateMealSuccessfully() {
        var input = new CreateMealUseCase.Input("Almoço", "Frango com batata doce");

        when(mealRepositoryMock.save(any(Meal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var output = createMealUseCase.execute(input);

        assertNotNull(output);
        assertNotNull(output.id());
        assertEquals("Almoço", output.name());
        assertEquals("Frango com batata doce", output.description());
        assertNotNull(output.consumedAt());

        verify(mealRepositoryMock, times(1)).save(any(Meal.class));
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando o nome da refeição for vazio")
    void shouldThrowExceptionWhenMealNameIsBlank() {
        var input = new CreateMealUseCase.Input("", "Descrição qualquer");

        assertThrows(IllegalArgumentException.class, () -> {
            createMealUseCase.execute(input);
        });

        verify(mealRepositoryMock, never()).save(any(Meal.class));
    }
}
