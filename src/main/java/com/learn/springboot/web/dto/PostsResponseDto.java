package com.learn.springboot.web.dto;

import com.learn.springboot.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsResponseDto {
    private Long id;
    private Long no;
    private String title;
    private String content;
    private String author;
    private Long viewCount;
    private LocalDateTime modifiedDate;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.no = entity.getNo();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.viewCount = entity.getViewCount();
        this.modifiedDate = entity.getModifiedDate();
    }
}
