package com.myco.users.services;

import com.myco.users.dtos.UploadedFileDto;

import java.util.List;

public interface UploadedFileService {

    List<UploadedFileDto> getFilesByPostId(Long postId);

}
