package com.myco.users.services;

import com.myco.users.entities.Vendor;
import com.myco.users.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public Vendor updateVendor(UUID id, Vendor vendorDetails) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        
        vendor.setName(vendorDetails.getName());
        vendor.setEmail(vendorDetails.getEmail());
        vendor.setMobileNumber(vendorDetails.getMobileNumber());
        vendor.setAddress(vendorDetails.getAddress());
        vendor.setStatus(vendorDetails.getStatus());
        
        return vendorRepository.save(vendor);
    }

    public void deleteVendor(UUID id) {
        vendorRepository.deleteById(id);
    }
}
