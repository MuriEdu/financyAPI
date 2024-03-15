package com.muriedu.financyapi.domain.repositories;

import com.muriedu.financyapi.domain.entities.AllocationEntity;
import com.muriedu.financyapi.domain.entities.CashEntity;
import com.muriedu.financyapi.domain.enums.AllocationTypes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AllocationRepository extends JpaRepository<AllocationEntity, UUID> {
    public List<AllocationEntity> findAllByCashId(CashEntity cash);
    public List<AllocationEntity> findAllByNameContainsAndCashId(String name, CashEntity cash);

    public List<AllocationEntity> findAllByTypeAndCashId(AllocationTypes type, CashEntity cash);

}
