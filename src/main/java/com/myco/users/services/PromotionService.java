package com.myco.users.services;

import com.myco.users.entities.Promotion;
import com.myco.users.repositories.PromotionRepository;
import com.myco.users.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.Objects;

@Service
public class PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private NotificationService notificationService;

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    public PromotionService() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public List<Promotion> getPromotionsByVendor(UUID vendorId) {
        return promotionRepository.findByVendorId(vendorId);
    }

    public Promotion getPromotionById(UUID id) {
        return promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
    }

    public Promotion savePromotion(Promotion promotion, MultipartFile file) {
        if (promotion.getVendor() != null && promotion.getVendor().getId() != null) {
             vendorRepository.findById(promotion.getVendor().getId())
                 .orElseThrow(() -> new RuntimeException("Vendor not found"));
        }
        if (file != null && !file.isEmpty()) {
            String fileName = storeFile(file);
            promotion.setImageUrl(fileName);
        }
        return promotionRepository.save(promotion);
    }

    public Promotion updatePromotion(UUID id, Promotion promotionDetails, MultipartFile file) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        
        promotion.setCode(promotionDetails.getCode());
        promotion.setDescription(promotionDetails.getDescription());
        promotion.setDiscountType(promotionDetails.getDiscountType());
        promotion.setDiscountValue(promotionDetails.getDiscountValue());
        promotion.setStartDate(promotionDetails.getStartDate());
        promotion.setEndDate(promotionDetails.getEndDate());
        promotion.setStatus(promotionDetails.getStatus());

        if (file != null && !file.isEmpty()) {
            String fileName = storeFile(file);
            promotion.setImageUrl(fileName);
        }
        
        return promotionRepository.save(promotion);
    }

    public void deletePromotion(UUID id) {
        promotionRepository.deleteById(id);
    }

    public void sendPromotionToConsumers(UUID promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        // In a real scenario, fetch actual consumer numbers from a repository
        List<String> consumerPhoneNumbers = List.of("+1234567890"); 

        // Construct public image URL (assuming a static file serving setup exists)
        String publicImageUrl = "http://localhost:8080/uploads/" + promotion.getImageUrl();
        String message = "Check out this new promotion: " + promotion.getDescription() + "\n" +
                         "View & Like here: http://localhost:3000/promotions/" + promotion.getId();

        for (String phone : consumerPhoneNumbers) {
            notificationService.sendWhatsAppMessage(phone, message, publicImageUrl);
        }
    }

    public void incrementViewCount(UUID id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        promotion.setViewCount(promotion.getViewCount() == null ? 1 : promotion.getViewCount() + 1);
        promotionRepository.save(promotion);
    }

    public void incrementLikeCount(UUID id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        promotion.setLikeCount(promotion.getLikeCount() == null ? 1 : promotion.getLikeCount() + 1);
        promotionRepository.save(promotion);
    }

    private String storeFile(MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}