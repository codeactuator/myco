package com.myco.users.repositories;

import com.myco.users.entities.ProductRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRegistrationRepository extends JpaRepository<ProductRegistration, Long> {
    List<ProductRegistration> findByUserId(UUID userId);
    boolean existsByProductInstanceId(UUID productInstanceId);
    Optional<ProductRegistration> findByProductInstanceId(UUID productInstanceId);
}