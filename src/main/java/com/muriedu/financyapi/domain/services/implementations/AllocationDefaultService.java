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
import java.util.Optional;
import java.util.UUID;

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
        boolean uniqueName = allocationRepository.findAllByNameContainsAndCashId(name, cash).isEmpty();
        if (uniqueName)
            return allocationRepository.save(
                    AllocationEntity.builder()
                            .name(name.toUpperCase())
                            .cashId(cash)
                            .crrCash(BigDecimal.ZERO)
                            .total(BigDecimal.ZERO)
                            .type(AllocationTypes.USER)
                            .build());
        else throw new AllocationCreationException("You already have an allocation with this name at season");
    }

    @Override
    public List<AllocationEntity> getAllByCash(CashEntity cash) {
        return allocationRepository.findAllByCashId(cash);
    }

    @Override
    public List<AllocationEntity> getByName(CashEntity cash, String name) {
        return allocationRepository.findAllByNameContainsAndCashId(name.toUpperCase(), cash);
    }

    @Override
    public List<AllocationEntity> getByType(CashEntity cash, AllocationTypes type) {
        return allocationRepository.findAllByTypeAndCashId(type, cash);
    }

    @Override
    public AllocationEntity getById(UUID id) {
        return allocationRepository.findById(id).orElseThrow(
                () -> new DataNotFoundedException("Allocation not founded")
        );
    }

    @Override
    public void update(AllocationEntity allocation) {
        getById(allocation.getId());
        allocationRepository.save(allocation);
    }

    @Override
    public void deleteAllByCash(CashEntity cash) {
        List<AllocationEntity> allocationEntities = allocationRepository.findAllByCashId(cash);
        allocationRepository.deleteAll(allocationEntities);
    }

    @Override
    public void deleteById(UUID allocation) {
        Optional<AllocationEntity> allocationEntity = allocationRepository.findById(allocation);
        allocationEntity.ifPresent(allocationRepository::delete);
    }
}