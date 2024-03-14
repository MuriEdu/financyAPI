package com.muriedu.financyapi.domain.services.implementations;

import com.muriedu.financyapi.domain.entities.AllocationEntity;
import com.muriedu.financyapi.domain.entities.CashEntity;
import com.muriedu.financyapi.domain.entities.SeasonEntity;
import com.muriedu.financyapi.domain.enums.AllocationTypes;
import com.muriedu.financyapi.domain.repositories.CashRepository;
import com.muriedu.financyapi.domain.services.CashService;
import com.muriedu.financyapi.exceptions.CashCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CashDefaultService implements CashService {

    private final CashRepository cashRepository;
    private final AllocationDefaultService allocationService;

    @Override
    public CashEntity create(SeasonEntity season) {
        List<CashEntity> cashs = cashRepository.findAllBySeason(season);
        if (cashs.isEmpty()){
            CashEntity cash = CashEntity.builder()
                    .season(season)
                    .cash(BigDecimal.ZERO)
                    .total(BigDecimal.ZERO)
                    .build();
            CashEntity savedCash = cashRepository.save(cash);
            allocationService.create(savedCash, AllocationTypes.EARNS);
            allocationService.create(savedCash, AllocationTypes.SPENDS);
            return savedCash;
        } else throw new CashCreationException("This season already has a cash");


    }


}
