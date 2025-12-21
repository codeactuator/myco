package com.myco.users.entities;

import com.myco.users.entities.Vendor;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String imageUrl;
    private String status; // ACTIVE, INACTIVE

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;
}