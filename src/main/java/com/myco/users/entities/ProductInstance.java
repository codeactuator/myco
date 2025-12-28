package com.myco.users.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "product_instances")
public class ProductInstance {
    @Id
    private UUID id; // This is the QR Code UUID

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}