package com.muriedu.financyapi.domain.services;

import com.muriedu.financyapi.domain.entities.AllocationEntity;
import com.muriedu.financyapi.domain.entities.CashEntity;
import com.muriedu.financyapi.domain.entities.SeasonEntity;
import com.muriedu.financyapi.domain.entities.UserEntity;

import java.util.List;

public interface CashService {
    public CashEntity create(SeasonEntity season);

}
