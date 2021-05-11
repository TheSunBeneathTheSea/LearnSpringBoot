package com.learn.springboot.web.dto;

import com.learn.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String boardName;
    @Setter
    private Long no;
    private String title;
    private String content;
    private String author;
    private String IPAddress;
    private Long viewCount;

    @Builder
    public PostsSaveRequestDto(String boardName, String title, String content, String author, String IPAddress, Long viewCount, BoardsDto boardsDto) {
        this.boardName = boardName;
        this.no = boardsDto.getPostsCount();
        this.title = title;
        this.content = content;
        this.author = author;
        this.IPAddress = IPAddress;
        this.viewCount = viewCount;
    }

    public Posts toEntity() {
        return Posts.builder()
                .boardName(boardName)
                .no(no)
                .title(title)
                .content(content)
                .author(author)
                .IPAddress(IPAddress)
                .build();
    }
}
