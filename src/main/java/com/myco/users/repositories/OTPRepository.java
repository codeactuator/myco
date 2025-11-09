package com.myco.users.repositories;

import com.myco.users.entities.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPRepository extends JpaRepository<OTP, Long> {
}
