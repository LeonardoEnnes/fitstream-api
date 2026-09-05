package com.dev.fitstream.workouts.infra.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "workouts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    private boolean completed;

    private LocalDateTime createdAt;
}
