package com.muriedu.financyapi.domain.services.implementations;

import com.muriedu.financyapi.DTOs.OperationDTO;
import com.muriedu.financyapi.domain.entities.AllocationEntity;
import com.muriedu.financyapi.domain.entities.OperationEntity;
import com.muriedu.financyapi.domain.enums.AllocationTypes;
import com.muriedu.financyapi.domain.repositories.OperationRepository;
import com.muriedu.financyapi.domain.services.OperationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class OperationDefaultService implements OperationService {

    private final AllocationDefaultService allocationService;
    private final OperationRepository operationRepository;

    @Override
    public OperationEntity create(UUID allocationId, @Valid OperationDTO operation) {
        AllocationEntity allocation = allocationService.getById(allocationId);
        if (allocation.getType().equals(AllocationTypes.SPENDS))
            allocation.setCrrCash(allocation.getCrrCash().subtract(operation.getOpValue()));
        else
            allocation.setCrrCash(allocation.getCrrCash().add(operation.getOpValue()));

        allocationService.update(allocation);
        return operationRepository.save(
                OperationEntity.builder()
                        .name(operation.getName())
                        .opValue(operation.getOpValue())
                        .allocation(allocation)
                        .build()
        );

    }
}