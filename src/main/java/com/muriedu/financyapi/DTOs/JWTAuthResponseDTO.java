package com.muriedu.financyapi.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTAuthResponseDTO {
    private String name;
    private String login;
    private String token;
}
