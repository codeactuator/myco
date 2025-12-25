package com.myco.users.entities;

import com.myco.users.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "system_users")
@Data
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    // Specific to VENDOR role. Nullable for Admin/Support.
    @Column(name = "vendor_id")
    private UUID vendorId;

    @Column(nullable = false)
    private String status = "ACTIVE";
}