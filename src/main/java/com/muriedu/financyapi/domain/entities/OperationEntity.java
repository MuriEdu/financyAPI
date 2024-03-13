package com.muriedu.financyapi.domain.entities;

import com.muriedu.financyapi.domain.enums.AllocationTypes;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "operation_tb")
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal opValue;

    @Column(nullable = false)
    private UUID allocation;



}
