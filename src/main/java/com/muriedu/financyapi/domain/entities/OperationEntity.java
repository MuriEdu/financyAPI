package com.muriedu.financyapi.domain.entities;

import com.muriedu.financyapi.domain.enums.AllocationTypes;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "operation_tb")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal opValue;

    @ManyToOne
    private AllocationEntity allocation;



}
