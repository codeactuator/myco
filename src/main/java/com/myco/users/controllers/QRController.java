package com.myco.users.controllers;

import com.myco.users.services.AppUserService;
import com.myco.users.services.QRCodeGeneratorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("v1/qr")
public class QRController {

    private final QRCodeGeneratorServiceImpl qrCodeServiceImpl;
    private final AppUserService appUserService;

    public QRController(QRCodeGeneratorServiceImpl qrCodeServiceImpl, AppUserService appUserService) {
        this.qrCodeServiceImpl = qrCodeServiceImpl;
        this.appUserService = appUserService;
    }

    @GetMapping
    public String ping(){
        return HttpStatus.OK.name();
    }

    @GetMapping(value = "/{userId}")
    public String getQRCode(@PathVariable String userId){
        return userId;
    }

    @PostMapping
    public String createQRCode(@RequestBody String mobileNumber){
        return appUserService.findByMobileNumber(mobileNumber).getId().toString();
    }
}
