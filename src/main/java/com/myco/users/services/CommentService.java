package com.myco.users.services;

import com.myco.users.dtos.CommentDto;
import com.myco.users.dtos.CommentRequest;

import java.util.List;

public interface CommentService {
    CommentDto addComment(CommentRequest request);
    List<CommentDto> getCommentsByPostId(Long postId);
}
