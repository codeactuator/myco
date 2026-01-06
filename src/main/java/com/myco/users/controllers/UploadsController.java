package com.myco.users.controllers;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
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

    @Autowired(required = false)
    private Storage storage;

    @Value("${gcp.bucket.name:myco-uploads}")
    private String bucketName;

    // Path to the directory where files are stored, based on your error log
    private final Path rootLocation = Paths.get(System.getProperty("user.dir"), "uploads");

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        // Try serving from Google Cloud Storage first
        if (storage != null) {
            try {
                Blob blob = storage.get(BlobId.of(bucketName, filename));
                if (blob != null && blob.exists()) {
                    ByteArrayResource resource = new ByteArrayResource(blob.getContent());
                    String contentType = blob.getContentType();
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                            .body(resource);
                }
            } catch (Exception e) {
                // Fallback to local storage if GCS fails or file not found
            }
        }

        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            
            if (resource.exists() || resource.isReadable()) {
                String contentType = Files.probeContentType(file);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}