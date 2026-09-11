package com.dev.fitstream.workouts.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Workout {

    private final UUID id;
    private String exercise;
    private int sets;
    private int reps;
    private double weight;
    private boolean completed;
    private final LocalDateTime createdAt;

    public Workout(UUID id, String exercise, int sets, int reps, double weight, boolean completed, LocalDateTime createdAt) {
        this.id = id != null ? id : UUID.randomUUID();
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.completed = completed;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    public void updateDetails(String exercise, int sets, int reps, double weight) {
        if (exercise == null || exercise.isBlank()) {
            throw new IllegalArgumentException("O nome do exercício não pode ser vazio.");
        }
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public void complete() {
        this.completed = true;
    }

    public UUID getId() { return id; }
    public String getExercise() { return exercise; }
    public int getSets() { return sets; }
    public int getReps() { return reps; }
    public double getWeight() { return weight; }
    public boolean isCompleted() { return completed; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
