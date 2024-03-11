package com.muriedu.financyapi.domain.services.implementations;

import com.muriedu.financyapi.domain.entities.SeasonEntity;
import com.muriedu.financyapi.domain.entities.UserEntity;
import com.muriedu.financyapi.domain.repositories.SeasonsRepository;
import com.muriedu.financyapi.domain.services.SeasonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SeasonDefaultService implements SeasonService {

    @Autowired
    private SeasonsRepository seasonsRepository;

    @Override
    public String create(UserEntity user) {

        LocalDateTime now = LocalDateTime.now();

        List<SeasonEntity> userSeasons = seasonsRepository.findAllByUserAndMonthAndYear(user, now.getMonthValue(), now.getYear());
        if (userSeasons.isEmpty()){
            SeasonEntity newSeason = SeasonEntity.builder()
                    .month(now.getMonthValue())
                    .year(now.getYear())
                    .user(user)
                    .build();
            return seasonsRepository.save(newSeason).getId().toString();
        } else {
            return null;
        }
    }

    @Override
    public boolean isActive(SeasonEntity season) {
        return false;
    }
}
