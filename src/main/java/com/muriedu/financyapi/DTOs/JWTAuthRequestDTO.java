package com.muriedu.financyapi.DTOs;

import lombok.Data;

@Data
public class JWTAuthRequestDTO {
    private String login;
    private String password;
}
