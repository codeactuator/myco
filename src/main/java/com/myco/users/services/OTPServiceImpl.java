package com.myco.users.services;

import com.myco.users.dtos.LoginRequest;
import com.myco.users.dtos.OTPDto;
import com.myco.users.entities.AppUser;
import com.myco.users.entities.OTP;
import com.myco.users.exceptions.InvalidOTPException;
import com.myco.users.repositories.AppUserRepository;
import com.myco.users.repositories.OTPRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OTPServiceImpl implements OTPService{


    private final AppUserRepository appUserRepository;
    private final OTPRepository otpRepository;

    public OTPServiceImpl(AppUserRepository appUserRepository, OTPRepository otpRepository) {
        this.appUserRepository = appUserRepository;
        this.otpRepository = otpRepository;
    }

    @Override
    public String generate(LoginRequest loginRequest) {
        // Find AppUser by mobile number
        AppUser appUser = appUserRepository.findByMobileNumber(loginRequest.getMobileNumber())
                .orElseGet(() -> {
                    // If user doesn't exist, create a new one and save it
                    AppUser newUser = new AppUser();
                    newUser.setMobileNumber(loginRequest.getMobileNumber());
                    appUserRepository.save(newUser);
                    return newUser;
                });

        // Generate OTPDto (4-digit random number in this example)
        String otp = generateOtp();

        // Save OTPDto into database (assuming an OTPDto entity and repository exist)
        OTP otpEntity = new OTP();
        otpEntity.setMobileNumber(loginRequest.getMobileNumber());
        otpEntity.setOtp(otp);
        otpEntity.setGeneratedAt(LocalDateTime.now());
        otpRepository.save(otpEntity);

        return otp; // Return the generated OTPDto (this can be sent via SMS or other means)
    }

    // Method to generate a 4-digit OTPDto
    private String generateOtp() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000); // Generate a random 4-digit number
        return String.valueOf(otp);
    }


    @Override
    public AppUser verify(OTPDto otpDto) {
        // Fetch OTPDto from DB. Replace this line with actual logic to retrieve the OTPDto from the database.
//        String otpInDB = otpRepository.findByMobileNumber(otpDto.getMobileNumber())
//                .map(OTPDto::getOtp)
//                .orElseThrow(() -> new InvalidOTPException("OTPDto not found for the provided mobile number"));

        String otpInDB = "1111";
        if (otpInDB.equalsIgnoreCase(otpDto.getOtp())) {
            AppUser appUser = appUserRepository.findByMobileNumber(otpDto.getMobileNumber())
                    .orElseThrow(() -> new RuntimeException("User not found with mobile number: " + otpDto.getMobileNumber()));

            appUser.setVerified(true);
            appUserRepository.save(appUser);
            return appUser;
        } else {
            throw new InvalidOTPException("Invalid OTPDto provided");
        }
    }

}
