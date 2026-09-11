package com.dev.fitstream.nutrition.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Meal {
    private final UUID id;
    private String name;
    private int calories;
    private int protein;
    private int carbs;
    private int fat;
    private LocalDateTime consumedAt;

    public Meal(UUID id, String name, int calories, int protein, int carbs, int fat, LocalDateTime consumedAt) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.consumedAt = consumedAt != null ? consumedAt : LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public int getCalories() { return calories; }
    public int getProtein() { return protein; }
    public int getCarbs() { return carbs; }
    public int getFat() { return fat; }
    public LocalDateTime getConsumedAt() { return consumedAt; }
}
