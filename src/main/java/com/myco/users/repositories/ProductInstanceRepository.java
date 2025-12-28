package com.myco.users.repositories;

import com.myco.users.entities.ProductInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProductInstanceRepository extends JpaRepository<ProductInstance, UUID> {
}