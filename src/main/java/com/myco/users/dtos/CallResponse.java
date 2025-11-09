package com.myco.users.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CallResponse {

    private String requestId;
    private int callDurationSeconds;
    private String info;
    private LocalDateTime createdAt;
}
