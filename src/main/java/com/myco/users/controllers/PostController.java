package com.myco.users.controllers;

import com.myco.users.dtos.PostRequestDto;
import com.myco.users.dtos.PostResponseDto;
import com.myco.users.dtos.UploadedFileDto;
import com.myco.users.services.PostService;
import com.myco.users.services.UploadedFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UploadedFileService uploadedFileService;


    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        PostResponseDto postResponseDto = postService.findById(id);
        return ResponseEntity.ok(postResponseDto);
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestParam("file") MultipartFile file,
                                           @RequestParam("title") String title,
                                           @RequestParam("postedBy") String postedBy,
                                           @RequestParam("postedFor") String postedFor,
                                           @RequestParam("latitude") String latitude,
                                           @RequestParam("longitude") String longitude) {
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setFile(file);
        postRequestDto.setTitle(title);
        postRequestDto.setPostedBy(postedBy);
        postRequestDto.setPostedFor(postedFor);
        postRequestDto.setLatitude(latitude);
        postRequestDto.setLongitude(longitude);
        PostResponseDto createdPost = postService.createPost(postRequestDto);
        String name = postRequestDto.getFile().getName();
        log.info("FileName: {}", name);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/by-user/{postedBy}")
    public ResponseEntity<List<PostResponseDto>> getPostsByUser(@PathVariable String postedBy) {
        List<PostResponseDto> posts = postService.getPostsByPostedBy(postedBy);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/for-user/{postedFor}")
    public ResponseEntity<List<PostResponseDto>> getPostsForUser(@PathVariable String postedFor) {
        List<PostResponseDto> posts = postService.getPostsByPostedFor(postedFor);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}/files")
    public ResponseEntity<List<UploadedFileDto>> getUploadedFilesForPost(@PathVariable Long postId) {
        List<UploadedFileDto> files = uploadedFileService.getFilesByPostId(postId);
        return ResponseEntity.ok(files);
    }
}
