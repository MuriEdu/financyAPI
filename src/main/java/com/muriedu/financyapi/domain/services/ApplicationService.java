package com.muriedu.financyapi.domain.services;

import com.muriedu.financyapi.DTOs.*;
import com.muriedu.financyapi.domain.entities.AllocationEntity;
import com.muriedu.financyapi.domain.entities.UserEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ApplicationService {

    public UserEntity createNewUser(UserRequestDTO newUser);
    public JWTAuthResponseDTO authUser(JWTAuthRequestDTO credentials);

    public void deleteUser(String login);

    public void updateUser(UserDTO userDTO, String crrLogin);

    public boolean validateToken(String token);

    public List<SeasonResponseDTO> getAllUserSeason(String login);

    public SeasonResponseDTO getCurrentSeason(String login);

    public SeasonDataDTO getSeasonData(String login, String SeasonId);

    public void setCash(String login, String cashId, BigDecimal valueToSet);

    //public AllocationEntity newAllocation()


}
