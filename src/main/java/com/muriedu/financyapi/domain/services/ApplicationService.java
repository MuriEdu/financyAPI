package com.muriedu.financyapi.domain.services;

import com.muriedu.financyapi.DTOs.JWTAuthRequestDTO;
import com.muriedu.financyapi.DTOs.JWTAuthResponseDTO;
import com.muriedu.financyapi.DTOs.UserDTO;
import com.muriedu.financyapi.DTOs.UserRequestDTO;
import com.muriedu.financyapi.domain.entities.UserEntity;

public interface ApplicationService {

    public UserEntity createNewUser(UserRequestDTO newUser);
    public JWTAuthResponseDTO authUser(JWTAuthRequestDTO credentials);

    public void deleteUser(String login);

    public void updateUser(UserDTO userDTO, String crrLogin);


}
