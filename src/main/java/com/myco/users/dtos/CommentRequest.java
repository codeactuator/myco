package com.myco.users.dtos;

import lombok.Data;

@Data
public class CommentRequest {
    private Long postId;
    private String text;
    private String commentedBy;
}
