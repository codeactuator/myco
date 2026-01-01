package com.myco.users.services;

import com.myco.users.dtos.CommentDto;
import com.myco.users.dtos.CommentRequest;
import com.myco.users.entities.AppUser;
import com.myco.users.entities.Comment;
import com.myco.users.entities.Post;
import com.myco.users.mappers.CommentMapper;
import com.myco.users.repositories.AppUserRepository;
import com.myco.users.repositories.CommentRepository;
import com.myco.users.repositories.PostRepository;
import com.myco.users.repositories.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public CommentDto addComment(CommentRequest request) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid postId: " + request.getPostId()));

        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setCommentedBy(request.getCommentedBy());
        comment.setPost(post);
        comment.setCommentedAt(LocalDateTime.now());

        Comment saved = commentRepository.save(comment);

        CommentDto dto = mapCommentToDto(saved);

        // Push to all subscribers of that post using DTO
        messagingTemplate.convertAndSend("/topic/posts/" + post.getId() + "/comments", dto);

        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid postId: " + postId));

        return commentRepository.findByPost(post)
                .stream()
                .map(this::mapCommentToDto)
                .collect(Collectors.toList());
    }

    private CommentDto mapCommentToDto(Comment comment) {
        UUID userId = UUID.fromString(comment.getCommentedBy());
        String userName = appUserRepository.findById(userId)
                .map(AppUser::getName)
                .or(() -> systemUserRepository.findById(userId).map(u -> "Myco Support"))
                .orElse("Anonymous");

        return CommentMapper.toDto(comment, userName);
    }
}
