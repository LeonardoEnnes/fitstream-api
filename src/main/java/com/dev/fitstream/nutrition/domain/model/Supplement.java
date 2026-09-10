package com.dev.fitstream.nutrition.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "supplements")
public class Supplement {

    @Id
    private UUID id;
    private String name;
    private int dosage;
    private String unit;
    private LocalDateTime takenAt;

    @Deprecated // Uso exclusivo do Hibernate
    protected Supplement() {}

    public Supplement(UUID id, String name, int dosage, String unit, LocalDateTime takenAt) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name;
        this.dosage = dosage;
        this.unit = unit;
        this.takenAt = takenAt != null ? takenAt : LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public int getDosage() { return dosage; }
    public String getUnit() { return unit; }
    public LocalDateTime getTakenAt() { return takenAt; }
}
