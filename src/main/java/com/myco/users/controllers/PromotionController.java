package com.myco.users.controllers;

import com.myco.users.entities.Promotion;
import com.myco.users.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/promotions")
@CrossOrigin(origins = "*")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @GetMapping("/vendor/{vendorId}")
    public List<Promotion> getPromotionsByVendor(@PathVariable UUID vendorId) {
        return promotionService.getPromotionsByVendor(vendorId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable UUID id) {
        return ResponseEntity.ok(promotionService.getPromotionById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Promotion createPromotion(@RequestPart("promotion") Promotion promotion,
                                     @RequestPart(value = "image", required = false) MultipartFile image) {
        return promotionService.savePromotion(promotion, image);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Promotion> updatePromotion(@PathVariable UUID id,
                                                     @RequestPart("promotion") Promotion promotion,
                                                     @RequestPart(value = "image", required = false) MultipartFile image) {
        return ResponseEntity.ok(promotionService.updatePromotion(id, promotion, image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable UUID id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<Void> sendPromotion(@PathVariable UUID id) {
        promotionService.sendPromotionToConsumers(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/view")
    public ResponseEntity<Void> incrementViewCount(@PathVariable UUID id) {
        promotionService.incrementViewCount(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> incrementLikeCount(@PathVariable UUID id) {
        promotionService.incrementLikeCount(id);
        return ResponseEntity.ok().build();
    }
}