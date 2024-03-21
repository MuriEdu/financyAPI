package com.muriedu.financyapi.DTOs;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AllocationDataDTO {

    String id;
    String name;
    BigDecimal crrCash;
    BigDecimal total;

}
