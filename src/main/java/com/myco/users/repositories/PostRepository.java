package com.myco.users.repositories;

import com.myco.users.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPostedBy(String postedBy);
    List<Post> findByPostedFor(String postedFor);
    List<Post> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

}
