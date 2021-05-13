package com.learn.springboot.domain.posts;

import com.learn.springboot.domain.BaseTimeEntity;
import com.learn.springboot.web.dto.BoardsDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long no;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @JoinColumn(name="BOARDS_NAME")
    @Column(nullable = false)
    private String boardName;

    private String author;

    private String IPAddress;

    private Long viewCount;

    @Builder
    public Posts(String boardName, String title, String content, String author, String IPAddress, Long no) {
        this.boardName = boardName;
        this.no = no;
        this.title = title;
        this.content = content;
        this.author = author;
        this.IPAddress = IPAddress;
        this.viewCount = 0L;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseViewCount(){
        this.viewCount++;
    }
}
