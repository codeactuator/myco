package com.myco.users.controllers;

import com.myco.users.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/posts")
@CrossOrigin(origins = "*")
public class PostFilesController {

    @Autowired
    @Qualifier("fileUploadService")
    private FileUploadService fileUploadService;

    @PostMapping("/{postId}/files")
    public ResponseEntity<String> uploadFileToPost(@PathVariable Long postId, @RequestParam("file") MultipartFile file) {
        try {
            fileUploadService.uploadFile(file, "SYSTEM", postId); // User ID is not critical for file attachment here
            return ResponseEntity.ok("File uploaded");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload file");
        }
    }
}