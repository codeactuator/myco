package com.myco.users.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class QRCode {

    @Id
    @GeneratedValue
    private Long id;
    private byte[] qrcode;
    @OneToOne
    private AppUser user;

}
