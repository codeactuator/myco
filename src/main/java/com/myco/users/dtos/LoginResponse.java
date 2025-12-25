package com.myco.users.dtos;

import com.myco.users.enums.UserRole;
import lombok.Data;

import java.util.UUID;

@Data
public class LoginResponse {
    private Long id;
    private String username;
    private String email;
    private UserRole role;
    private UUID vendorId;
    private String status;
}