package com.muriedu.financyapi.domain.services;

import com.muriedu.financyapi.DTOs.SeasonResponseDTO;
import com.muriedu.financyapi.domain.entities.SeasonEntity;
import com.muriedu.financyapi.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeasonService {

    public String create(UserEntity user);

    public boolean isActive(SeasonEntity season);

    public List<SeasonEntity> getAllSeasons(UserEntity user);

    public SeasonEntity getSeasonByDate(UserEntity user, SeasonResponseDTO season);

    public void deleteAllUserSeasons(UserEntity user);

}
