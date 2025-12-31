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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

    //@Value("${file.upload-dir}")
    private String uploadDir = Paths.get(System.getProperty("user.dir"), "uploads").toString();
    //@Value("${file.access-location}")
    private String accessLocation = Paths.get(System.getProperty("user.dir"), "uploads").toString() + java.io.File.separator;

    @Autowired
    private UploadedFileRepository uploadedFileRepository;

    @Autowired
    private PostRepository postRepository;


    @Override
    public CompletableFuture<String> uploadFile(MultipartFile file, String userId, Long postId) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid postId: " + postId);
        }
        Post post = postOpt.get();

        UploadedFile uploadedFile = new UploadedFile(
                userId,
                fileName,
                accessLocation + fileName,
                LocalDateTime.now(),
                post
        );
        uploadedFileRepository.save(uploadedFile);

        String result = "File uploaded successfully: " + fileName + " by user: " + userId;
        return CompletableFuture.completedFuture(result);
    }
}
