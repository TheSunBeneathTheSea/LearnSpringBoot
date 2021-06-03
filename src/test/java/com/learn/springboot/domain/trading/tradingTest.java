package com.learn.springboot.domain.trading;

import com.learn.springboot.domain.user.Role;
import com.learn.springboot.domain.user.User;
import com.learn.springboot.domain.user.UserRepository;
import com.learn.springboot.service.trading.TradingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class tradingTest {
    @Autowired
    StockInfoRepository stockInfoRepository;
    @Autowired
    StockPriceRepository stockPriceRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    HoldingStocksRepository holdingStocksRepository;
    @Autowired
    TradingLogRepository tradingLogRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TradingService tradingService;

    @Test
    @Transactional
    public void buyingStock(){
        StockInfo stockInfo = StockInfo.builder()
                .companyCode("021").companyName("사성").industry("방구석").build();
        stockInfoRepository.save(stockInfo);

        List<StockInfo> listsi = stockInfoRepository.findAllDesc();

        StockPrice stockPrice = StockPrice.builder()
                .stockInfo(stockInfo).realTimePrice(5378L).clsPrice(4000L).build();
        stockPriceRepository.save(stockPrice);

        List<StockPrice> listsp = stockPriceRepository.findAllDesc();

        User user = User.builder()
                .name("CHOI").picture("wow").email("dev@wow.com").role(Role.USER).build();

        Member member = Member.builder()
                .accountBalance(9000000L).name("TW").user(user).build();
        memberRepository.save(member);

        HoldingStocks holdingStocks = HoldingStocks.builder()
                .shareAmount(21L).member(member).stockInfo(stockInfo).build();
        holdingStocksRepository.save(holdingStocks);


        // 매수 주문
        tradingService.buyStock("021", 32L, "TW");

        List<HoldingStocks> holdingStocksList = holdingStocksRepository.findAll();
        HoldingStocks holdingStocks_to =  holdingStocksList.get(0);

        List<TradingLog> tradingLog = tradingLogRepository.findAllDesc();
        TradingLog testLog = tradingLog.get(0);

        Member testMember = memberRepository.findMemberByNameDesc("TW");


        assertThat(holdingStocks_to.getMember().getName()).isEqualTo("TW");
        assertThat(holdingStocks_to.getStockInfo().getCompanyCode()).isEqualTo("021");
        assertThat(holdingStocks_to.getShareAmount()).isEqualTo(53L);

        assertThat(testLog.isBuying()).isEqualTo(true);
        assertThat(testLog.getTradeAmount()).isEqualTo(32L);
        assertThat(testLog.getTradePrice()).isEqualTo(5378L);
        assertThat(testLog.getMember().getName()).isEqualTo("TW");
        assertThat(testLog.getStockInfo().getCompanyCode()).isEqualTo("021");

        assertThat(testMember.getName()).isEqualTo("TW");
        assertThat(testMember.getAccountBalance()).isEqualTo(8827904L);
    }
}
