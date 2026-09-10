package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.repository.MealRepository;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.dev.fitstream.nutrition.domain.model.Meal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

class FindAllMealsUseCaseTest {

    private MealRepository mealRepositoryMock;
    private FindAllMealsUseCase findAllMealsUseCase;

    @BeforeEach
    void setUp() {
        mealRepositoryMock = mock(MealRepository.class);
        findAllMealsUseCase = new FindAllMealsUseCase(mealRepositoryMock);
    }

    @Test
    @DisplayName("Should return an list of all meals registered")
    void shouldReturnAllMeals() {
        Meal meal1 = new Meal(null, "Café da Manhã", "Pão e ovo", LocalDateTime.now());
        Meal meal2 = new Meal(null, "Almoço", "Arroz e feijão", LocalDateTime.now());
        when(mealRepositoryMock.findAll()).thenReturn(List.of(meal1, meal2));

        List<Meal> result = findAllMealsUseCase.execute();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Café da Manhã", result.get(0).getName());
        assertEquals("Almoço", result.get(1).getName());

        verify(mealRepositoryMock, times(1)).findAll();
    }
}
