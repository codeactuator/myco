package com.myco.users.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostRequestDto {
    MultipartFile file;
    private String title;
    private String postedBy;
    private String postedFor;
    private String longitude;
    private String latitude;
}
