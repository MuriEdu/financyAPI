package com.muriedu.financyapi.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SeasonResponseDTO {


    private Integer month;
    private Integer year;
    private String id;


}
