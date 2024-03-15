package com.muriedu.financyapi.DTOs;

import com.muriedu.financyapi.domain.entities.AllocationEntity;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OperationDTO {

    private String name;

    @PositiveOrZero
    private BigDecimal opValue;

    private AllocationEntity allocation;
}
