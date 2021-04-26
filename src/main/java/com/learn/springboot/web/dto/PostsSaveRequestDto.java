package com.learn.springboot.web.dto;

import com.learn.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private String IPAddress;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author, String IPAddress) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.IPAddress = IPAddress;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .IPAddress(IPAddress)
                .build();
    }
}
