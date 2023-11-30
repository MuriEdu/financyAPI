package com.muri.financyAPI.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_movements")
public class MovementModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String title;
    private String description;
    private double movementValue;

    @Column(nullable = false)
    private UUID partition;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
