package com.dev.fitstream.workouts.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Workout {

    private final UUID id;
    private String title;
    private String description;
    private boolean completed;
    private final LocalDateTime createdAt;

    public Workout(UUID id, String title, String description, boolean completed, LocalDateTime createdAt) {
        this.id = id != null ? id : UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    public void updateDetails(String title, String description) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("O título do treino não pode ser vazio.");
        }
        this.title = title;
        this.description = description;
    }

    public void markAsCompleted() {
        this.completed = true;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
