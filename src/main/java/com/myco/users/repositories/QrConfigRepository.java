package com.myco.users.repositories;

import com.myco.users.entities.QrConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrConfigRepository extends JpaRepository<QrConfig, UUID> {
    Optional<QrConfig> findByReferenceId(String referenceId);
}