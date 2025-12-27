package com.myco.users.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myco.users.entities.Promotion;
import com.myco.users.entities.Vendor;
import com.myco.users.services.PromotionService;
import com.myco.users.services.VendorService;

@RestController
@RequestMapping("v1/vendors")
@CrossOrigin(origins = "*")
public class VendorController {
    @Autowired
    private VendorService vendorService;
    
    @Autowired
    private PromotionService promotionService;

    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendor(@PathVariable UUID id) {
        return ResponseEntity.ok(vendorService.getVendorById(id));
    }
    
    @GetMapping("/{id}/analytics")
    public ResponseEntity<Map<String, Object>> getVendorAnalytics(@PathVariable UUID id) {
        List<Promotion> promotions = promotionService.getPromotionsByVendor(id);
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("totalPromotions", promotions.size());
        analytics.put("activePromotions", promotions.stream().filter(p -> "ACTIVE".equalsIgnoreCase(p.getStatus())).count());
        analytics.put("totalViews", promotions.stream().mapToLong(p -> p.getViewCount() == null ? 0 : p.getViewCount()).sum());
        analytics.put("totalLikes", promotions.stream().mapToLong(p -> p.getLikeCount() == null ? 0 : p.getLikeCount()).sum());
        return ResponseEntity.ok(analytics);
    }

    @PostMapping
    public Vendor createVendor(@RequestBody Vendor vendor) {
        return vendorService.saveVendor(vendor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable UUID id, @RequestBody Vendor vendor) {
        return ResponseEntity.ok(vendorService.updateVendor(id, vendor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable UUID id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.ok().build();
    }
}
