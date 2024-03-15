package com.muriedu.financyapi.domain.repositories;

import com.muriedu.financyapi.domain.entities.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OperationRepository extends JpaRepository<OperationEntity, UUID> {
}
