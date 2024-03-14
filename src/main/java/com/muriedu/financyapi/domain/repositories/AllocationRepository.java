package com.muriedu.financyapi.domain.repositories;

import com.muriedu.financyapi.domain.entities.AllocationEntity;
import com.muriedu.financyapi.domain.entities.CashEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AllocationRepository extends JpaRepository<AllocationEntity, UUID> {
    public List<AllocationEntity> findAllByCashId(CashEntity cash);
    public Optional<AllocationEntity> findByNameAndCashId(String name, CashEntity cash);

}