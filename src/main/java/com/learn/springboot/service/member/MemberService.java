package com.learn.springboot.service.member;

import com.learn.springboot.domain.member.Member;
import com.learn.springboot.domain.member.MemberRepository;
import com.learn.springboot.domain.user.User;
import com.learn.springboot.web.dto.MemberSaveRequestDto;
import com.learn.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public String save(MemberSaveRequestDto memberSaveRequestDto){
        memberRepository.save(memberSaveRequestDto.toEntity());

        return "저장되었습니다";
    }

    @Transactional(readOnly = true)
    public boolean isNotRegisteredUser(Long id){
        return memberRepository.findMemberByUserId(id) == null;
    }

    public Member findMemberByUserId(Long id){
        return memberRepository.findMemberByUserId(id);
    }
}
