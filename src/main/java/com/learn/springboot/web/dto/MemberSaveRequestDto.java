package com.learn.springboot.web.dto;

import com.learn.springboot.domain.member.Member;
import com.learn.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private User user;
    private String name;
    private Long accountBalance;
    private List<String> targetStockIdList;

    @Builder
    public MemberSaveRequestDto(User user, String name, List<String> targetStockIdList){
        this.user = user;
        this.name = name;
        this.targetStockIdList = targetStockIdList;
    }

    public Member toEntity(){
        return Member.builder()
                .user(user)
                .name(name)
                .build();
    }
}
