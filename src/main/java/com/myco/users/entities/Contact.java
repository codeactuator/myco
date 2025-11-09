package com.myco.users.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Contact {

    @Id
    @GeneratedValue
    private long id;
    private String relation;
    private String contactName;
    private String contactNumber;
    @ManyToOne
    private AppUser appUser;
    @CreatedDate
    private long createdAt;
    @LastModifiedDate
    private long modifiedAt;
}
