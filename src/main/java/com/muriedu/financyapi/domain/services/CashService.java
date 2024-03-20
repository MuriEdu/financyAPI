package com.muriedu.financyapi.domain.services;

import com.muriedu.financyapi.DTOs.OperationDTO;
import com.muriedu.financyapi.domain.entities.CashEntity;
import com.muriedu.financyapi.domain.entities.SeasonEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CashService {
    public CashEntity create(SeasonEntity season);

    public CashEntity set(BigDecimal initial, SeasonEntity season);

    public CashEntity getBySeason(SeasonEntity season);

    public void delete(SeasonEntity season);

    public void deleteAll(List<CashEntity> cashesToDelete);

    public BigDecimal calcCash(SeasonEntity season);

}
