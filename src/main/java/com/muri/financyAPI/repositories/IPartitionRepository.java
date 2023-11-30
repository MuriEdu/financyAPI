package com.muri.financyAPI.repositories;

import com.muri.financyAPI.models.PartitionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPartitionRepository extends JpaRepository<PartitionModel, UUID> {
    Optional<PartitionModel> findById(UUID id);
    List<PartitionModel> findByCreatedAtGreaterThanEqual(LocalDateTime createdAt);
}
