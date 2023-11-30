package com.muri.financyAPI.repositories;

import com.muri.financyAPI.models.MovementModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IMovementRepository extends JpaRepository<MovementModel, UUID> {
}
