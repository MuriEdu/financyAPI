package com.muriedu.financyapi.domain.services.implementations;

import com.muriedu.financyapi.DTOs.OperationDTO;
import com.muriedu.financyapi.domain.entities.AllocationEntity;
import com.muriedu.financyapi.domain.entities.CashEntity;
import com.muriedu.financyapi.domain.entities.SeasonEntity;
import com.muriedu.financyapi.domain.enums.AllocationTypes;
import com.muriedu.financyapi.domain.repositories.AllocationRepository;
import com.muriedu.financyapi.domain.repositories.CashRepository;
import com.muriedu.financyapi.domain.services.CashService;
import com.muriedu.financyapi.exceptions.CashCreationException;
import com.muriedu.financyapi.exceptions.DataNotFoundedException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CashDefaultService implements CashService {

    private final CashRepository cashRepository;

    @Override
    public CashEntity create(SeasonEntity season) {
        Optional<CashEntity> cash = cashRepository.findBySeason(season);
        if (cash.isEmpty()){
            CashEntity cashToCreate = CashEntity.builder()
                    .season(season)
                    .cash(BigDecimal.ZERO)
                    .nonAllocated(BigDecimal.ZERO)
                    .total(BigDecimal.ZERO)
                    .build();
            return cashRepository.save(cashToCreate);
        } else throw new CashCreationException("This season already has a cash");


    }

    @Override
    public CashEntity set(BigDecimal initial, SeasonEntity season) {
        CashEntity cash = getBySeason(season);
        cash.setNonAllocated(initial);
        return cashRepository.save(cash);
    }

    @Override
    public CashEntity getBySeason(SeasonEntity season) {
        Optional<CashEntity> cash = cashRepository.findBySeason(season);
        return cash.orElseThrow(
                () -> new DataNotFoundedException("Cash not founded")
        );
    }

    @Override
    public void delete(SeasonEntity season) {
        Optional<CashEntity> cash = cashRepository.findBySeason(season);
        cash.ifPresent(foundCash -> {
            allocationService.deleteAllByCash(foundCash);
            cashRepository.delete(foundCash);
        });
    }

    @Override
    public void deleteAll(List<CashEntity> cashesToDelete) {
        cashRepository.deleteAll(cashesToDelete);
    }

    @Override
    public BigDecimal calcCash(SeasonEntity season) {
        CashEntity cash = getBySeason(season);
        List<AllocationEntity> allocations = allocationService.getAllByCash(cash);
        allocations.forEach(allocation -> {
            cash.setCash(cash.getCash().add(allocation.getCrrCash()));
        });
        cash.setCash(cash.getCash().add(cash.getNonAllocated()));
        cashRepository.save(cash);
        return cash.getCash();
    }


}
