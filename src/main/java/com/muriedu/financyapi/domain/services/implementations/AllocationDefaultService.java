package com.muriedu.financyapi.domain.services.implementations;

import com.muriedu.financyapi.domain.entities.AllocationEntity;
import com.muriedu.financyapi.domain.entities.CashEntity;
import com.muriedu.financyapi.domain.enums.AllocationTypes;
import com.muriedu.financyapi.domain.repositories.AllocationRepository;
import com.muriedu.financyapi.domain.services.AllocationService;
import com.muriedu.financyapi.exceptions.AllocationCreationException;
import com.muriedu.financyapi.exceptions.DataNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AllocationDefaultService implements AllocationService {

    private final AllocationRepository allocationRepository;

    @Override
    public AllocationEntity create(CashEntity cash, AllocationTypes type) {
        String name = type.equals(AllocationTypes.EARNS) ? "EARNS" : "SPENDS";
        return allocationRepository.save(
                AllocationEntity.builder()
                .name(name)
                .cashId(cash)
                .crrCash(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .type(type)
                .build());
    }

    @Override
    public AllocationEntity create(CashEntity cash, String name) {
        boolean uniqueName = allocationRepository.findByNameAndCashId(name, cash).isEmpty();
        if (uniqueName)
            return allocationRepository.save(
                    AllocationEntity.builder()
                            .name(name.toUpperCase())
                            .cashId(cash)
                            .crrCash(BigDecimal.ZERO)
                            .total(BigDecimal.ZERO)
                            .type(AllocationTypes.USER)
                            .build());
        else throw new AllocationCreationException("You already have an allocation with this name");
    }

    @Override
    public List<AllocationEntity> getAllByCash(CashEntity cash) {
        return allocationRepository.findAllByCashId(cash);
    }

    @Override
    public AllocationEntity getByName(CashEntity cash, String name) {

        return allocationRepository.findByNameAndCashId(name.toUpperCase(), cash).orElseThrow(
                () -> new DataNotFoundedException("Allocation not founded"));
    }
}
