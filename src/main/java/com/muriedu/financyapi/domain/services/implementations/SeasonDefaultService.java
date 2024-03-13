package com.muriedu.financyapi.domain.services.implementations;

import com.muriedu.financyapi.DTOs.SeasonDTO;
import com.muriedu.financyapi.domain.entities.SeasonEntity;
import com.muriedu.financyapi.domain.entities.UserEntity;
import com.muriedu.financyapi.domain.repositories.SeasonsRepository;
import com.muriedu.financyapi.domain.services.SeasonService;
import com.muriedu.financyapi.exceptions.DataNotFoundedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    public SeasonEntity getSeasonByDate(UserEntity user, SeasonDTO season) {
        return seasonsRepository.findByUserAndMonthAndYear(user, season.getMonth(), season.getYear())
                .orElseThrow(() -> new DataNotFoundedException("Season not founded"));
    }

    @Override
    public void deleteAllUserSeasons(UserEntity user) {
        List<SeasonEntity> seasons = getAllSeasons(user);
        seasonsRepository.deleteAll(seasons);
    }


}
