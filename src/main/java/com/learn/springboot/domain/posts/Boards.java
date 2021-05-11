package com.learn.springboot.domain.posts;

import com.learn.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Boards {

    @Id
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long postsCount;

    @Builder
    public Boards(String name, Long postsCount) {
        this.name = name;
        this.postsCount = postsCount;
    }

    public void countUpdate(){
        this.postsCount++;
    }
}
