package com.muriedu.financyapi.domain.services.implementations;

import com.muriedu.financyapi.DTOs.SeasonResponseDTO;
import com.muriedu.financyapi.domain.entities.SeasonEntity;
import com.muriedu.financyapi.domain.entities.UserEntity;
import com.muriedu.financyapi.domain.repositories.SeasonsRepository;
import com.muriedu.financyapi.domain.services.SeasonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
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
        Integer seasonMonth = season.getMonth();
        Integer seasonYear = season.getYear();

        LocalDateTime now = LocalDateTime.now();
        Integer crrMonth = now.getMonthValue();
        Integer crrYear = now.getYear();
        return (Objects.equals(seasonMonth, crrMonth) && Objects.equals(seasonYear, crrYear));
    }

    @Override
    public List<SeasonEntity> getAllSeasons(UserEntity user) {
        return seasonsRepository.findAllByUser(user);
    }

    @Override
    public SeasonEntity getSeasonByDate(UserEntity user, SeasonResponseDTO season) {
        return seasonsRepository.findAllByUserAndMonthAndYear(user, season.getMonth(), season.getYear()).get(0);
    }


}
