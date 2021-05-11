package com.learn.springboot.web.dto;

import com.learn.springboot.domain.posts.Boards;
import com.learn.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardsDto {
    private String name;
    private Long postsCount;

    @Builder
    public BoardsDto(Boards entity){
        this.name = entity.getName();
        this.postsCount = entity.getPostsCount();
    }

    public Boards toEntity() {
        return Boards.builder()
                .name(name)
                .postsCount(postsCount)
                .build();
    }
}
