package com.myco.users.controllers;

import com.myco.users.services.ShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("v1/short-links")
@CrossOrigin(origins = "*")
public class ShortLinkController {

    @Autowired
    private ShortLinkService shortLinkService;

    @PostMapping("/bulk")
    public ResponseEntity<Map<UUID, String>> createBulkShortLinks(@RequestBody List<UUID> uuids) {
        return ResponseEntity.ok(shortLinkService.createBulkShortLinks(uuids));
    }

    @GetMapping("/{code}")
    public ResponseEntity<Map<String, UUID>> resolveShortLink(@PathVariable String code) {
        UUID uuid = shortLinkService.resolveShortCode(code);
        return ResponseEntity.ok(Map.of("uuid", uuid));
    }
}