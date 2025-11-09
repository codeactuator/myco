package com.myco.users.mappers;

import com.myco.users.dtos.PostResponseDto;
import com.myco.users.entities.AppUser;
import com.myco.users.entities.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {

    public static PostResponseDto toDto(Post post, AppUser appUser) {
        if (post == null) return null;

        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setPostedBy(post.getPostedBy());
        dto.setPostedByName(appUser.getName());
        dto.setPostedFor(post.getPostedFor());
        dto.setLatitude(Double.valueOf(post.getLatitude()));
        dto.setLongitude(Double.valueOf(post.getLongitude()));
        dto.setCreatedAt(post.getCreatedAt());

        return dto;
    }

    public static List<PostResponseDto> toDtoList(List<Post> posts, AppUser appUser) {
        return posts.stream()
                .map(post -> toDto(post, appUser))
                .collect(Collectors.toList());
    }

}
