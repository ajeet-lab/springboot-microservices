package com.ajeet.blog.ajeetblog.servicesImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ajeet.blog.ajeetblog.entites.Category;
import com.ajeet.blog.ajeetblog.entites.Post;
import com.ajeet.blog.ajeetblog.entites.User;
import com.ajeet.blog.ajeetblog.exceptions.ResourceNotFoundException;
import com.ajeet.blog.ajeetblog.payloads.PostDto;
import com.ajeet.blog.ajeetblog.payloads.PostResponse;
import com.ajeet.blog.ajeetblog.repos.CateRepo;
import com.ajeet.blog.ajeetblog.repos.PostRepo;
import com.ajeet.blog.ajeetblog.repos.UserRepo;
import com.ajeet.blog.ajeetblog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CateRepo cateRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User ", "UserId ", userId));

        Category category = this.cateRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category ", "CategoryId ", userId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setPostImage("default.png");
        post.setCategory(category);
        post.setUser(user);
        post.setCreatedAt(new Date());
        post.setUpdatedAt(new Date());
        Post savedPost = this.postRepo.save(post);
        return this.modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePostById(Integer postId, PostDto postDto){
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post ", "PostId ", postId));

        post.setPostName(postDto.getPostName());
        post.setPostContent(postDto.getPostContent());
        post.setPostImage(postDto.getPostImage());

        Post updatedPost = postRepo.save(post);

        return  this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public List<PostDto> findByUserDto(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User ", "UserId ", userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> findByCategoryDto(Integer categoryId) {
        Category category = this.cateRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category ", "CategoryId ", categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable page = PageRequest.of(pageNumber, pageSize, sort);        
        Page<Post> posts = this.postRepo.findAll(page);
        List<Post> allPost = posts.getContent();

        List<PostDto> postDtos = allPost.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtos);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setFirstPage(posts.isFirst());
        postResponse.setLastPage(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public void deletePostById(Integer postId){
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

        postRepo.delete(post);
    }

    @Override
    public List<PostDto> findByPostNameContaining(String keyword) {
        List<Post> posts = postRepo.findByPostNameContaining(keyword);

        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> findByPostContent(String keyword) {
        List<Post> posts = postRepo.findByPostContent("%"+keyword+"%");
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
    

    

}
