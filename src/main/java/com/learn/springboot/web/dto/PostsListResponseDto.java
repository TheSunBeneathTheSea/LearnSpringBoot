package com.learn.springboot.web.dto;
import com.learn.springboot.domain.posts.Posts;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private String boardName;
    private Long id;
    private String title;
    private String author;
    private Long viewCount;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity) {
        this.boardName = entity.getBoardName();
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.viewCount = entity.getViewCount();
        this.modifiedDate = entity.getModifiedDate();
    }
}
