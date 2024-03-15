package com.muriedu.financyapi.domain.services;

import com.muriedu.financyapi.DTOs.OperationDTO;
import com.muriedu.financyapi.domain.entities.OperationEntity;
import jakarta.validation.Valid;

import java.util.UUID;

public interface OperationService {

    public OperationEntity create(UUID allocationId, @Valid OperationDTO operation);
}
