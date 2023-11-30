package com.ajeet.blog.ajeetblog.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ajeet.blog.ajeetblog.entites.Category;
import com.ajeet.blog.ajeetblog.entites.Post;
import com.ajeet.blog.ajeetblog.entites.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findByCategory(Category category); 
    List<Post> findByUser(User user);  

    List<Post> findByPostNameContaining(String keyword);

    @Query("select p from Post p where p.postContent like :key")
    List<Post> findByPostContent(@Param("key") String keyword);
}
