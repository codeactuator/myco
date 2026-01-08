package com.myco.users.controllers;

import com.myco.users.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/v1/uploads")
@CrossOrigin(origins = "*")
public class UploadsController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource resource = fileStorageService.loadFileAsResource(filename);
        if (resource != null && (resource.exists() || resource.isReadable())) {
            String contentType = "application/octet-stream";
            String lowerCaseFileName = filename.toLowerCase();
            if (lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else if (lowerCaseFileName.endsWith(".png")) {
                contentType = "image/png";
            } else if (lowerCaseFileName.endsWith(".gif")) {
                contentType = "image/gif";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}