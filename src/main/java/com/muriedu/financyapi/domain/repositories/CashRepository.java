package com.muriedu.financyapi.domain.repositories;

import com.muriedu.financyapi.domain.entities.CashEntity;
import com.muriedu.financyapi.domain.entities.SeasonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CashRepository extends JpaRepository<CashEntity, UUID> {
    public List<CashEntity> findAllBySeason(SeasonEntity season);
}