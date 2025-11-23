package com.myco.users.controllers;

import com.myco.users.dtos.LoginRequest;
import com.myco.users.dtos.OTPDto;
import com.myco.users.entities.AppUser;
import com.myco.users.services.AppUserService;
import com.myco.users.services.OTPService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/otp")
public class OTPController {

	private final OTPService otpService;
	private final AppUserService appUserService;

	public OTPController(OTPService otpService, AppUserService appUserService) {
		this.otpService = otpService;
		this.appUserService = appUserService;
	}

	@PostMapping("/send")
	public ResponseEntity<String> send(@RequestBody LoginRequest loginRequest){
		String generated = otpService.generate(loginRequest);
		return ResponseEntity.ok(generated);
	}

	@PostMapping("/verify")
    public ResponseEntity<AppUser> verify(@RequestBody OTPDto otpDto){
		AppUser appUser = otpService.verify(otpDto);
		return ResponseEntity.ok().body(appUser);
    }

}
