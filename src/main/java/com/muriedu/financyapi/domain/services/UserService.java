package com.muriedu.financyapi.domain.services;

import com.muriedu.financyapi.DTOs.JWTAuthRequestDTO;
import com.muriedu.financyapi.DTOs.JWTAuthResponseDTO;
import com.muriedu.financyapi.DTOs.UserRequestDTO;
import com.muriedu.financyapi.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

public interface UserService {
    public UserEntity create(UserRequestDTO newUser);

    public JWTAuthResponseDTO auth(JWTAuthRequestDTO credentials);

    public UserEntity loadByLogin(String login);

    public void delete(String login);
}