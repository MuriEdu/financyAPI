package com.muriedu.financyapi.domain.services;

import com.muriedu.financyapi.DTOs.SeasonDTO;
import com.muriedu.financyapi.domain.entities.SeasonEntity;
import com.muriedu.financyapi.domain.entities.UserEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeasonService {

    public SeasonEntity create(UserEntity user);

    public List<SeasonEntity> getAllSeasons(UserEntity user);

    public SeasonEntity getSeasonByDate(UserEntity user, SeasonDTO season);

    public void deleteAll(List<SeasonEntity> seasonsToDelete);



}
