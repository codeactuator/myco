package com.myco.users.controllers;

import com.myco.users.entities.Post;
import com.myco.users.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts/by-date")
    public ResponseEntity<List<Post>> getPostsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
            @RequestParam("endDate")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate) {

        List<Post> posts = postService.getPostsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(posts);
    }
}
