package com.myco.users.controllers;

import com.myco.users.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("v1/files")
public class FileUploadController {

    @Autowired
    @Qualifier("asyncFileUploadService")
    private FileUploadService asyncFileUploadService;

    @Autowired
    @Qualifier("fileUploadService")
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") String userId,
            @RequestParam("postId") Long postId) {
        try {
            String result = fileUploadService.uploadFile(file, userId, postId).get();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Could not upload the file: " + e.getMessage());
        }
    }

    @PostMapping("/uploadAsync")
    public ResponseEntity<String> uploadFileAsync(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") String userId,
            @RequestParam("postId") Long postId) {
        try {
            System.out.println("Asyncupload service thorugh Completable.");
            CompletableFuture<String> future = asyncFileUploadService.uploadFile(file, userId, postId);
            String result = future.get(); // or return future directly if using WebAsyncTask
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Async upload failed: " + e.getMessage());
        }
    }

}
