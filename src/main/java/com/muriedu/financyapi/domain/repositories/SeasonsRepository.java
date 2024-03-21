package com.muriedu.financyapi.domain.repositories;

import com.muriedu.financyapi.domain.entities.SeasonEntity;
import com.muriedu.financyapi.domain.entities.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeasonsRepository extends JpaRepository<SeasonEntity, UUID> {
    public List<SeasonEntity> findAllByUserAndMonthAndYear(UserEntity user, Integer month, Integer year);
    public List<SeasonEntity> findAllByUser(UserEntity user);
    Optional<SeasonEntity> findByUserAndMonthAndYear(UserEntity user, Integer month, Integer year);
    Optional<SeasonEntity> findByUserAndId(UserEntity user, UUID id);
}
