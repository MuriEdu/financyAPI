package com.muriedu.financyapi.domain.services;

import com.muriedu.financyapi.domain.entities.AllocationEntity;
import com.muriedu.financyapi.domain.entities.CashEntity;
import com.muriedu.financyapi.domain.entities.SeasonEntity;
import com.muriedu.financyapi.domain.enums.AllocationTypes;

import java.util.List;
import java.util.UUID;

public interface AllocationService {
    public AllocationEntity create(CashEntity cash, AllocationTypes type);
    public AllocationEntity create(CashEntity cash, String name);
    public List<AllocationEntity> getAllByCash(CashEntity cash);
    public List<AllocationEntity> getByName(CashEntity cash, String name);
    public List<AllocationEntity> getByType(CashEntity cash, AllocationTypes type);
    public AllocationEntity getById(UUID id);
    public void update(AllocationEntity allocation);
    public void deleteAllByCash(CashEntity cash);
    public void deleteById(UUID allocation);
}
