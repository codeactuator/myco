package com.myco.users.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponseDto {

    private Long id;
    private String title;
    private String postedBy;
    private String postedByName;
    private String postedFor;
    private LocalDateTime createdAt;
    private double longitude;
    private double latitude;
}
