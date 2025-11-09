package com.myco.users.services;

import com.myco.users.dtos.LoginRequest;
import com.myco.users.dtos.OTPDto;
import com.myco.users.entities.AppUser;

public interface OTPService {

    String generate(LoginRequest loginRequest);
    AppUser verify(OTPDto otpDto);
}
