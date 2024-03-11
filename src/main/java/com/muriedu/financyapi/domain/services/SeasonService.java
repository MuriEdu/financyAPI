package com.muriedu.financyapi.domain.services;

import com.muriedu.financyapi.domain.entities.SeasonEntity;
import com.muriedu.financyapi.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface SeasonService {

    public String create(UserEntity user);

    public boolean isActive(SeasonEntity season);

}
