package com.myco.users.controllers;

import com.myco.users.entities.AppUser;
import com.myco.users.entities.Post;
import com.myco.users.services.AppUserService;
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
    private AppUserService appUserService;

    @GetMapping("/alerts/user/{userId}")
    public List<Post> getAlertsForUser(@PathVariable String userId) {
        // 1. Get the user's mobile number to find who has listed them as a contact
        AppUser user = appUserService.find(userId);
        if (user == null) {
            return List.of();
        }
        String userMobile = user.getMobileNumber();

        // 2. Fetch posts where:
        //    a) The post is directly for the user (postedFor.id = userId)
        //    b) OR the post is for someone who has this user as a contact (via Contact table)
        String queryStr = "SELECT p FROM Post p " +
                "WHERE p.postedFor = :userId " +
                "OR p.postedFor IN (SELECT CAST(c.appUser.id as string) FROM Contact c WHERE c.contactNumber = :userMobile) " +
                "ORDER BY p.createdAt DESC";

        return entityManager.createQuery(queryStr, Post.class)
                .setParameter("userId", userId)
                .setParameter("userMobile", userMobile)
                .getResultList();
    }

    @GetMapping("/alerts/all")
    public List<Post> getAllAlerts() {
        return entityManager.createQuery("SELECT p FROM Post p ORDER BY p.createdAt DESC", Post.class)
                .getResultList();
    }
}