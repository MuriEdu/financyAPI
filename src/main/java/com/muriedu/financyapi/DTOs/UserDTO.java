package com.muriedu.financyapi.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class UserDTO {
    String name;
    String login;
}
