package com.myco.users.dtos;

import com.myco.users.entities.AppUser;
import com.myco.users.entities.Contact;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CallRequest {

    private UUID id;
    private String fromUserId;
    private String toUserId;
    private String message;
    private String latitude;
    private String longitude;
    private LocalDateTime createdAt;
    private Contact contact;
    private AppUser owner;
    private AppUser caller;
}
