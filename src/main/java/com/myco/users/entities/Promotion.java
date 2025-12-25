package com.myco.users.entities;

import com.myco.users.enums.DiscountType;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "promotions")
@Data
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String code;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    
    private BigDecimal discountValue;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // ACTIVE, INACTIVE
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;
}