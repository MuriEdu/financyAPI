package com.muri.financyAPI.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_partitions")
public class PartitionModel
{
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String title;
    private String Description;
    private Double partitionValue;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
