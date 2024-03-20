package com.muriedu.financyapi.domain.services;

import com.muriedu.financyapi.DTOs.OperationDTO;
import com.muriedu.financyapi.domain.entities.AllocationEntity;
import com.muriedu.financyapi.domain.entities.OperationEntity;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface OperationService {

    public OperationEntity create(AllocationEntity allocation, @Valid OperationDTO operation);
    public OperationEntity getById(UUID uuid, AllocationEntity allocation);
    public void update(OperationEntity operation);
    public List<OperationEntity> getAllByAllocation (AllocationEntity allocation);
    public OperationEntity getByName(String name, AllocationEntity allocation);
    public void deleteAll(List<OperationEntity> operationEntities);
    public void deleteById(UUID id, AllocationEntity allocation);
}
