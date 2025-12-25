package com.myco.users.services;

import com.myco.users.dtos.SystemLoginRequest;
import com.myco.users.dtos.SystemLoginResponse;
import com.myco.users.dtos.UserCreationRequest;
import com.myco.users.entities.SystemUser;
import com.myco.users.enums.UserRole;
import com.myco.users.repositories.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SystemUserService {

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Transactional
    public SystemUser createUser(UserCreationRequest request) {
        if (systemUserRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (systemUserRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        SystemUser user = new SystemUser();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        if (request.getRole() == UserRole.VENDOR) {
            if (request.getVendorId() == null) {
                throw new IllegalArgumentException("Vendor ID is required for Vendor users");
            }
            user.setVendorId(request.getVendorId());
        } else {
            user.setVendorId(null);
        }

        return systemUserRepository.save(user);
    }

    public List<SystemUser> getAllUsers() {
        return systemUserRepository.findAll();
    }

    @Transactional
    public SystemUser updateUser(Long id, UserCreationRequest request) {
        SystemUser user = systemUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setEmail(request.getEmail());
        // Note: In a real app, handle password updates more securely (e.g. only if non-empty)
        user.setPassword(request.getPassword()); 
        user.setRole(request.getRole());
        user.setVendorId(request.getRole() == UserRole.VENDOR ? request.getVendorId() : null);

        return systemUserRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        systemUserRepository.deleteById(id);
    }

    @Transactional
    public SystemUser toggleUserStatus(Long id) {
        SystemUser user = systemUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setStatus("ACTIVE".equals(user.getStatus()) ? "INACTIVE" : "ACTIVE");
        return systemUserRepository.save(user);
    }

    public SystemLoginResponse authenticate(SystemLoginRequest request) {
        SystemUser user = systemUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new IllegalArgumentException("Account is inactive. Please contact admin.");
        }

        SystemLoginResponse response = new SystemLoginResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setVendorId(user.getVendorId());
        response.setStatus(user.getStatus());
        
        return response;
    }
}