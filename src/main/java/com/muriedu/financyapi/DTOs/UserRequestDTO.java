package com.muriedu.financyapi.DTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotEmpty(message = "Name field is required!")
    String name;

    @NotEmpty(message = "Login field is required!")
    String login;

    @NotEmpty(message = "Password field is required")
    String password;
}
