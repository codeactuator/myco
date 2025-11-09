package com.myco.users.mappers;


import com.myco.users.dtos.CommentDto;
import com.myco.users.entities.Comment;
import com.myco.users.entities.Post;

public class CommentMapper {

    // Example using UserRepository to resolve userName
    public static CommentDto toDto(Comment comment, String userName) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setCommentedBy(comment.getCommentedBy());
        dto.setUserName(userName);
        dto.setPostId(comment.getPost() != null ? comment.getPost().getId() : null);
        dto.setCommentedAt(comment.getCommentedAt());
        return dto;
    }

    public static Comment toEntity(CommentDto dto, Post post) {
        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setText(dto.getText());
        comment.setCommentedBy(dto.getCommentedBy());
        comment.setPost(post);
        comment.setCommentedAt(dto.getCommentedAt());
        return comment;
    }
}
