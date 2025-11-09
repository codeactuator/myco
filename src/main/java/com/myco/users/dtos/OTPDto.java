package com.myco.users.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OTPDto {

    private String mobileNumber;
    private String otp;
    private LocalDateTime generatedAt;
}
