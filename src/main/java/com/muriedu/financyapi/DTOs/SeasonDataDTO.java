package com.muriedu.financyapi.DTOs;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SeasonDataDTO {

    Integer month;
    Integer year;
    CashDataDTO cash;
    List<AllocationDataDTO> allocations;

}
