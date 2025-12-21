package com.myco.users.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "qr_configs")
@Data
public class QrConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String referenceId; // "default" or productId

    private int size;
    private String fgColor;
    private String bgColor;
    private boolean rounded;
}