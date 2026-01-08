package com.myco.users.controllers;

import com.myco.users.entities.Post;
import com.myco.users.dtos.PostResponseDto;
import com.myco.users.services.PostService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/posts")
@CrossOrigin(origins = "*")
public class AlertsController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostService postService;

    @GetMapping("/alerts/user/{userId}")
    public List<PostResponseDto> getAlertsForUser(@PathVariable String userId) {
        return postService.getPostsByPostedFor(userId);
    }

    @GetMapping("/alerts/all")
    public List<Post> getAllAlerts() {
        return entityManager.createQuery("SELECT p FROM Post p ORDER BY p.createdAt DESC", Post.class)
                .getResultList();
    }
}