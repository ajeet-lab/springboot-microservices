package com.ajeet.blog.ajeetblog.entites;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @NotEmpty
    @Column(name="title", nullable = false, length = 100)
    private String postName;

    @NotEmpty
    @Column(name="content", nullable = false)
    private String postContent;

    @Column(name="image")
    private String postImage;

    private Date createdAt;
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name="categoryId", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name="userId", nullable = false)
    private User user;

}
