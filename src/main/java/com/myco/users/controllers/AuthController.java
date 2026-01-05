package com.myco.users.controllers;

import com.myco.users.dtos.SystemLoginRequest;
import com.myco.users.dtos.SystemLoginResponse;
import com.myco.users.services.SystemUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private SystemUserService systemUserService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody SystemLoginRequest request) {
        try {
            SystemLoginResponse response = systemUserService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}