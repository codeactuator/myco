package com.myco.users.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UploadedFileDto {

    private Long id;
    private String userId;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadedAt;
    private Long postId;
    // getters/setters
}
