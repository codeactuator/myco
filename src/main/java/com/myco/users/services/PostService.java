package com.myco.users.services;

import com.myco.users.dtos.PostRequestDto;
import com.myco.users.dtos.PostResponseDto;
import com.myco.users.entities.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostService {

    PostResponseDto createPost(PostRequestDto postRequestDto);
    PostResponseDto findById(Long id);
    List<PostResponseDto> getPostsByPostedBy(String postedBy);
    List<PostResponseDto> getPostsByPostedFor(String postedFor);
    List<Post> getPostsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);


}
