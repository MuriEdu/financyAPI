package com.muriedu.financyapi.domain.repositories;

import com.muriedu.financyapi.domain.entities.AllocationEntity;
import com.muriedu.financyapi.domain.entities.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OperationRepository extends JpaRepository<OperationEntity, UUID> {
    public Optional<OperationEntity> findByIdAndAllocation(UUID id, AllocationEntity allocation);
    public List<OperationEntity> findAllByAllocation(AllocationEntity allocation);
    public Optional<OperationEntity> findByNameContainsIgnoreCaseAndAllocation(String name, AllocationEntity allocation);
}
