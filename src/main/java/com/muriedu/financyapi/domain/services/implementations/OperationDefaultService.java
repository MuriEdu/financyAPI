package com.muriedu.financyapi.domain.services.implementations;

import com.muriedu.financyapi.DTOs.OperationDTO;
import com.muriedu.financyapi.domain.entities.AllocationEntity;
import com.muriedu.financyapi.domain.entities.OperationEntity;
import com.muriedu.financyapi.domain.enums.AllocationTypes;
import com.muriedu.financyapi.domain.repositories.OperationRepository;
import com.muriedu.financyapi.domain.services.OperationService;
import com.muriedu.financyapi.exceptions.DataNotFoundedException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OperationDefaultService implements OperationService {

    private final AllocationDefaultService allocationService;
    private final OperationRepository operationRepository;

    @Override
    public OperationEntity create(AllocationEntity allocation, @Valid OperationDTO operation) {
        allocationService.getById(allocation.getId(), allocation.getCashId());
        if (allocation.getType().equals(AllocationTypes.EARNS))
            allocation.setCrrCash(allocation.getCrrCash().add(operation.getOpValue()));
        else
            allocation.setCrrCash(allocation.getCrrCash().subtract(operation.getOpValue()));

        allocationService.update(allocation);
        return operationRepository.save(
                OperationEntity.builder()
                        .name(operation.getName())
                        .opValue(operation.getOpValue())
                        .allocation(allocation)
                        .build()
        );

    }

    @Override
    public OperationEntity getById(UUID id, AllocationEntity allocation) {
        return operationRepository.findByIdAndAllocation(id, allocation).orElseThrow(
                () -> new DataNotFoundedException("Operation not founded")
        );
    }

    @Override
    public void update(OperationEntity operation) {
        getById(operation.getId() ,operation.getAllocation());
        operationRepository.save(operation);
    }

    @Override
    public List<OperationEntity> getAllByAllocation(AllocationEntity allocation) {
        return operationRepository.findAllByAllocation(allocation);
    }

    @Override
    public OperationEntity getByName(String name, AllocationEntity allocation) {
        return operationRepository.findByNameContainsIgnoreCaseAndAllocation(name, allocation).orElseThrow(
                () -> new DataNotFoundedException("Operation not founded")
        );
    }

    @Override
    public void deleteAll(List<OperationEntity> operationsToDelete) {
        operationRepository.deleteAll(operationsToDelete);
    }

    @Override
    public void deleteById(UUID id, AllocationEntity allocation) {
        OperationEntity operationToDelete = getById(id, allocation);
        operationRepository.delete(operationToDelete);

        updateAllocation(allocation);

    }

    private void updateAllocation(AllocationEntity allocation) {
        if(allocation.getType().equals(AllocationTypes.EARNS)){
            allocation.setCrrCash(allocation.getCrrCash().subtract(allocation.getTotal()));
            allocation.setTotal(BigDecimal.ZERO);
        } else {
            allocation.setCrrCash(allocation.getCrrCash().add(allocation.getTotal()));
            allocation.setTotal(BigDecimal.ZERO);
        }
        allocationService.update(allocation);
    }


}