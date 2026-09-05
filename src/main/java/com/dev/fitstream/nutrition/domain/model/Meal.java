package com.dev.fitstream.nutrition.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Meal {
    private final UUID id;
    private String name;
    private String description;
    private LocalDateTime consumedAt;

    public Meal(UUID id, String name, String description, LocalDateTime consumedAt) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.consumedAt = consumedAt != null ? consumedAt : LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDateTime getConsumedAt() { return consumedAt; }

}
