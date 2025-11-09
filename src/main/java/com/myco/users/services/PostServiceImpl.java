package com.myco.users.services;

import com.myco.users.dtos.PostRequestDto;
import com.myco.users.dtos.PostResponseDto;
import com.myco.users.entities.AppUser;
import com.myco.users.entities.Post;
import com.myco.users.exceptions.FileCouldNotUploadException;
import com.myco.users.exceptions.PostNotFoundException;
import com.myco.users.exceptions.UserNotFoundException;
import com.myco.users.mappers.PostMapper;
import com.myco.users.repositories.AppUserRepository;
import com.myco.users.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    @Qualifier("fileUploadService")
    private FileUploadService fileUploadService;

    @Override
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setPostedBy(postRequestDto.getPostedBy());
        post.setPostedFor(postRequestDto.getPostedFor());
        post.setLatitude(postRequestDto.getLatitude());
        post.setLongitude(postRequestDto.getLongitude());
        Post saved = postRepository.save(post);

        try {
            fileUploadService.uploadFile(postRequestDto.getFile(), post.getPostedBy(), saved.getId());
        } catch (IOException e) {
            throw new FileCouldNotUploadException("Could not upload file!");
        }

        AppUser appUser = appUserRepository.findById(UUID.fromString(post.getPostedBy()))
                .orElseThrow(() -> new UserNotFoundException("User not found "
                        + post.getPostedBy()));

        PostResponseDto postResponseDto = PostMapper.toDto(post, appUser);
        return postResponseDto;
    }

    @Override
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post Not Found"));
        AppUser appUser = appUserRepository.findById(UUID.fromString(post.getPostedBy()))
                .orElseThrow(() -> new UserNotFoundException("User not found" + post.getPostedBy()));
        return PostMapper.toDto(post, appUser);
    }

    @Override
    public List<PostResponseDto> getPostsByPostedBy(String postedBy) {
        AppUser appUser = appUserRepository.findById(UUID.fromString(postedBy))
                .orElseThrow(() -> new UserNotFoundException("User not found: " + postedBy));
        List<Post> postList = postRepository.findByPostedBy(postedBy);
        return PostMapper.toDtoList(postList, appUser);
    }

    @Override
    public List<PostResponseDto> getPostsByPostedFor(String postedFor) {
        List<Post> postList = postRepository.findByPostedFor(postedFor);
        List<PostResponseDto> postResponseDtoList = postList.stream()
                .map(post -> {
                    AppUser appUser = appUserRepository.findById(UUID.fromString(post.getPostedBy()))
                            .orElseThrow(() -> new UserNotFoundException("User not found: " + post.getPostedBy()));
                    return PostMapper.toDto(post, appUser);
                })
                .collect(Collectors.toList());
        return postResponseDtoList;
    }

    @Override
    public List<Post> getPostsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return postRepository.findByCreatedAtBetween(startDate, endDate);
    }

}
