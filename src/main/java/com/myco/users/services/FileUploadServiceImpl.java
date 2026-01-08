package com.myco.users.services;

import com.myco.users.entities.Post;
import com.myco.users.entities.UploadedFile;
import com.myco.users.repositories.PostRepository;
import com.myco.users.repositories.UploadedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {


    @Autowired
    private UploadedFileRepository uploadedFileRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public CompletableFuture<String> uploadFile(MultipartFile file, String userId, Long postId) throws IOException {
        String fileName = fileStorageService.storeFile(file);

        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid postId: " + postId);
        }
        Post post = postOpt.get();

        UploadedFile uploadedFile = new UploadedFile(
                userId,
                fileName,
                fileName, // Storing filename as path since retrieval is centralized
                LocalDateTime.now(),
                post
        );
        uploadedFileRepository.save(uploadedFile);

        String result = "File uploaded successfully: " + fileName + " by user: " + userId;
        return CompletableFuture.completedFuture(result);
    }
}
