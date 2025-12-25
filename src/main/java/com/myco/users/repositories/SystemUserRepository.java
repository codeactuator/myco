package com.myco.users.repositories;

import com.myco.users.entities.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {
    Optional<SystemUser> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}