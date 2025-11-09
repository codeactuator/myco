package com.myco.users.controllers;

import com.myco.users.services.AppUserService;
import com.myco.users.services.QRCodeGeneratorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*")
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

    @GetMapping(value = "/{userId}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getQRCode(@PathVariable String userId){
        String userUrl = appUserService.createUserUrl(userId);
        byte[] image = new byte[0];
        try {
            image = qrCodeServiceImpl.create(userUrl,250,250);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    @PostMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] createQRCode(@RequestBody String mobileNumber){
        String userUrl = appUserService.createUserUrl(mobileNumber);
        byte[] image = new byte[0];
        try {
            image = qrCodeServiceImpl.create(userUrl,250,250);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
