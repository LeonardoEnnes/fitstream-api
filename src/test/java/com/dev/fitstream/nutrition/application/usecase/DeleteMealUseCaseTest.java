package com.dev.fitstream.nutrition.application.usecase;

import com.dev.fitstream.nutrition.domain.repository.MealRepository;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import java.util.UUID;

class DeleteMealUseCaseTest {

    private MealRepository mealRepositoryMock;
    private DeleteMealUseCase deleteMealUseCase;

    @BeforeEach
    void setUp() {
        mealRepositoryMock = mock(MealRepository.class);
        deleteMealUseCase = new DeleteMealUseCase(mealRepositoryMock);
    }

    @Test
    @DisplayName("Should delete a meal with success by ID")
    void shouldDeleteMealSuccessfully() {
        UUID mealId = UUID.randomUUID();
        doNothing().when(mealRepositoryMock).delete(mealId);

        assertDoesNotThrow(() -> deleteMealUseCase.execute(mealId));

        verify(mealRepositoryMock, times(1)).delete(mealId);
    }
}
