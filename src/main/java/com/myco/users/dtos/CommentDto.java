package com.myco.users.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private String text;
    private String commentedBy;
    private String userName;
    private Long postId;
    private LocalDateTime commentedAt;
}
