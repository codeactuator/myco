package com.myco.users.controllers;

import com.myco.users.dtos.UserCreationRequest;
import com.myco.users.entities.SystemUser;
import com.myco.users.services.SystemUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/system-users")
public class AdminSystemUserController {

    @Autowired
    private SystemUserService systemUserService;

    @PostMapping
    public ResponseEntity<?> createSystemUser(@Valid @RequestBody UserCreationRequest request) {
        try {
            SystemUser createdUser = systemUserService.createUser(request);
            return ResponseEntity.ok("System User created successfully with ID: " + createdUser.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<SystemUser>> getAllUsers() {
        return ResponseEntity.ok(systemUserService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreationRequest request) {
        return ResponseEntity.ok(systemUserService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        systemUserService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> toggleUserStatus(@PathVariable Long id) {
        return ResponseEntity.ok(systemUserService.toggleUserStatus(id));
    }
}