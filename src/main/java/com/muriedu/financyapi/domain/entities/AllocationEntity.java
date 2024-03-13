package com.muriedu.financyapi.domain.entities;

import com.muriedu.financyapi.domain.enums.AllocationTypes;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "allocations_tb")
public class AllocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private BigDecimal crrCash;

    @Column(nullable = false)
    private AllocationTypes type;

    @ManyToOne
    private CashEntity cashId;
}
