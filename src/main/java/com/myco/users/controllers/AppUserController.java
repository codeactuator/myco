package com.myco.users.controllers;

import com.myco.users.dtos.LoginRequest;
import com.myco.users.entities.AppUser;
import com.myco.users.services.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
public class AppUserController {



    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService){
        this.appUserService = appUserService;
    }

    @GetMapping
    public String ping(){
        return HttpStatus.OK.name();
    }

    @GetMapping("/{mobileNumber}")
    public AppUser findByMobileNumber(String mobileNumber){
        AppUser appUser = appUserService.findByMobileNumber(mobileNumber);
        return appUser;
    }

    @PostMapping("/verified")
    public AppUser verified(@RequestBody LoginRequest loginRequest){
        AppUser appUser = appUserService.findByMobileNumber(loginRequest.getMobileNumber());
        return appUser;
    }

    @PostMapping
    public AppUser create(@RequestBody @Valid AppUser appUser){
        return appUserService.save(appUser);
    }
}
