package com.ajeet.blog.ajeetblog.services;

import java.util.List;

import com.ajeet.blog.ajeetblog.payloads.PostDto;
import com.ajeet.blog.ajeetblog.payloads.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePostById(Integer postId, PostDto postDto);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PostDto getPostById(Integer postId);

    List<PostDto> findByUserDto(Integer userId);

    List<PostDto> findByCategoryDto(Integer categoryId);

    void deletePostById(Integer postId);

    List<PostDto> findByPostNameContaining(String keyword);

    List<PostDto> findByPostContent(String keyword);

}
