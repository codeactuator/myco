package com.myco.users.repositories;

import com.myco.users.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, UUID> {
    List<Promotion> findByVendorId(UUID vendorId);
}