package com.myco.users.entities;

import com.myco.users.entities.Product;
import com.myco.users.entities.Vendor;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private LocalDateTime createdAt;

    @ElementCollection
    @CollectionTable(name = "order_qrcodes", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "qrcode")
    private List<String> qrcodes;
}