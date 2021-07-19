package com.learn.springboot.domain.member;

import com.learn.springboot.domain.trading.StockInfo;
import com.learn.springboot.domain.trading.StockInfoRepository;
import com.learn.springboot.domain.user.Role;
import com.learn.springboot.domain.user.User;
import com.learn.springboot.domain.user.UserRepository;
import com.learn.springboot.service.member.MemberService;
import com.learn.springboot.service.trading.TradingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class memberTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StockInfoRepository stockInfoRepository;
    @Autowired
    TradingService tradingService;
    @Autowired
    MemberService memberService;

    @Test
    @Transactional
    public void registerMember(){
        Set<StockInfo> target = new HashSet<>();

        StockInfo stockInfo = StockInfo.builder()
                .companyCode("021").companyName("사성").industry("방구석").build();
        stockInfoRepository.save(stockInfo);
        target.add(stockInfo);

        stockInfo = StockInfo.builder()
                .companyCode("037").companyName("팔데").industry("음향").build();
        stockInfoRepository.save(stockInfo);
        target.add(stockInfo);

        stockInfo = StockInfo.builder()
                .companyCode("298").companyName("구업").industry("전자기기").build();
        stockInfoRepository.save(stockInfo);
        target.add(stockInfo);

        List<StockInfo> listsi = stockInfoRepository.findAllDesc();

        User user = User.builder()
                .name("CHOI").picture("wow").email("dev@wow.com").role(Role.USER).build();

        userRepository.save(user);
        System.out.println(memberService.isNotRegisteredUser(1L));

        Member member = Member.builder()
                .user(user).name("High").accountBalance(0L).build();

        memberRepository.save(member);
        System.out.println(memberService.isNotRegisteredUser(1L));

        List<Member> listmem = memberRepository.findAllDesc();

        assertThat(member.getUser()).isEqualTo(user);
        assertThat(member.getName()).isEqualTo("High");
        assertThat(member.getAccountBalance()).isEqualTo(0L);

        member.updateTarget(target);

        assertThat(member.getTargetStocks()).isNotNull().containsAll(target);
    }
}
