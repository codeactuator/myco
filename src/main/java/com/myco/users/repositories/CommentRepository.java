package com.myco.users.repositories;

import com.myco.users.entities.Comment;
import com.myco.users.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
