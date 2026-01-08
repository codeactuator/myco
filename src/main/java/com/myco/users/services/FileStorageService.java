package com.myco.users.services;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    @Autowired(required = false)
    private Storage storage;

    @Value("${gcp.bucket.name:myco-uploads}")
    private String bucketName;

    private final Path rootLocation = Paths.get("uploads").toAbsolutePath().normalize();

    public FileStorageService() {
        try {
            Files.createDirectories(rootLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        if (storage != null) {
            try {
                BlobId blobId = BlobId.of(bucketName, fileName);
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
                storage.create(blobInfo, file.getBytes());
                return fileName;
            } catch (IOException ex) {
                throw new RuntimeException("Could not store file " + fileName + " in GCS.", ex);
            }
        } else {
            try {
                Path targetLocation = this.rootLocation.resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                return fileName;
            } catch (IOException ex) {
                throw new RuntimeException("Could not store file " + fileName + " locally.", ex);
            }
        }
    }

    public Resource loadFileAsResource(String fileName) {
        if (storage != null) {
            try {
                Blob blob = storage.get(BlobId.of(bucketName, fileName));
                if (blob != null && blob.exists()) {
                    return new ByteArrayResource(blob.getContent());
                }
            } catch (Exception e) {
                // Fallback to local if GCS fails or file not found
                throw new RuntimeException("file could not be found in GCS.", e);
            }
        }
        
        try {
            Path filePath = this.rootLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) return resource;
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Malformed URL in GCS.", ex);
         }
        return null;
    }
}