package com.learn.springboot.domain.posts;

import com.learn.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    private String IPAddress;

    @Builder
    public Posts(String title, String content, String author, String IPAddress) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.IPAddress = IPAddress;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
