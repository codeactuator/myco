package com.myco.users.services;

import com.myco.users.dtos.UploadedFileDto;
import com.myco.users.entities.UploadedFile;
import com.myco.users.repositories.UploadedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UploadedFileServiceImpl implements UploadedFileService {

    @Autowired
    private UploadedFileRepository uploadedFileRepository;

    @Override
    public List<UploadedFileDto> getFilesByPostId(Long postId) {
        List<UploadedFile> uploadedFiles = uploadedFileRepository.findByPostId(postId);
        List<UploadedFileDto> uploadedFileDtos = uploadedFiles.stream()
                .map(uploadedFile -> {
                    UploadedFileDto dto = new UploadedFileDto();
                    dto.setId(uploadedFile.getId());
                    dto.setUploadedAt(uploadedFile.getUploadedAt());
                    dto.setFileName(uploadedFile.getFileName());
                    dto.setFilePath(uploadedFile.getFilePath());
                    dto.setPostId(uploadedFile.getPost().getId());
                    dto.setUserId(uploadedFile.getUserId());
                    return dto;
                }).collect(Collectors.toList());
        return uploadedFileDtos;
    }
}
