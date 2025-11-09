package com.myco.users.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface FileUploadService {
    CompletableFuture<String> uploadFile(MultipartFile file, String userId, Long postId) throws IOException;
}
