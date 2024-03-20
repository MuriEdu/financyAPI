package com.muriedu.financyapi.domain.services.implementations;

import com.muriedu.financyapi.DTOs.SeasonDTO;
import com.muriedu.financyapi.domain.entities.SeasonEntity;
import com.muriedu.financyapi.domain.entities.UserEntity;
import com.muriedu.financyapi.domain.repositories.SeasonsRepository;
import com.muriedu.financyapi.domain.services.CashService;
import com.muriedu.financyapi.domain.services.SeasonService;
import com.muriedu.financyapi.exceptions.DataNotFoundedException;
import com.muriedu.financyapi.exceptions.SeasonCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SeasonDefaultService implements SeasonService {


    private final SeasonsRepository seasonsRepository;

    @Override
    public SeasonEntity create(UserEntity user) {

        LocalDateTime now = LocalDateTime.now();

        List<SeasonEntity> userSeasons = seasonsRepository.findAllByUserAndMonthAndYear(user, now.getMonthValue(), now.getYear());
        if (userSeasons.isEmpty()){
            SeasonEntity newSeason = SeasonEntity.builder()
                    .month(now.getMonthValue())
                    .year(now.getYear())
                    .user(user)
                    .build();

            return seasonsRepository.save(newSeason);
        } else {
            throw new SeasonCreationException("This user already has an season at this month");
        }
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
    public void deleteAll(List<SeasonEntity> seasonsToDelete) {
        seasonsRepository.deleteAll(seasonsToDelete);
    }


}
