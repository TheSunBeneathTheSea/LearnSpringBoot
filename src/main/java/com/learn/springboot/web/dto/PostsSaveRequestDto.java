package com.learn.springboot.web.dto;

import com.learn.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private Integer boardId;
    private String title;
    private String content;
    private String author;
    private String IPAddress;
    private Long viewCount;

    @Builder
    public PostsSaveRequestDto(Integer boardId, String title, String content, String author, String IPAddress, Long viewCount) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.IPAddress = IPAddress;
        this.viewCount = viewCount;
    }

    public Posts toEntity() {
        return Posts.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .author(author)
                .IPAddress(IPAddress)
                .build();
    }
}
