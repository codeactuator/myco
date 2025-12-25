package com.myco.users.dtos;

import com.myco.users.enums.UserRole;
import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private String username;
    private String email;
    private UserRole role;
    private Long vendorId;
    private String status;
}