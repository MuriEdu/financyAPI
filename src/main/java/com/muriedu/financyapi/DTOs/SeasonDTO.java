package com.muriedu.financyapi.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeasonDTO {

    private Integer month;
    private Integer year;

}
