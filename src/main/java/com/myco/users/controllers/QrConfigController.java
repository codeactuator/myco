package com.myco.users.controllers;

import com.myco.users.services.QrConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("v1/qr-configs")
@CrossOrigin(origins = "*")
public class QrConfigController {
    @Autowired
    private QrConfigService qrConfigService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getConfigs() {
        return ResponseEntity.ok(qrConfigService.getAllConfigs());
    }

    @PostMapping
    public ResponseEntity<Void> saveConfigs(@RequestBody Map<String, Object> payload) {
        qrConfigService.saveConfigs(payload);
        return ResponseEntity.ok().build();
    }
}