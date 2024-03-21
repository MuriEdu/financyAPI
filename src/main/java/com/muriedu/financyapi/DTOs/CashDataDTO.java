package com.muriedu.financyapi.DTOs;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CashDataDTO {

    String id;
    BigDecimal cash;
    BigDecimal total;

}
