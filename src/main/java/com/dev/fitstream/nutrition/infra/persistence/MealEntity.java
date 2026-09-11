package com.dev.fitstream.nutrition.infra.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "meals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    private int calories;
    private int protein;
    private int carbs;
    private int fat;
    private LocalDateTime consumedAt;
}
