package com.muriedu.financyapi.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "cash_tb")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal cash = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal nonAllocated = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    @OneToOne
    private SeasonEntity season;

}
