package com.learn.springboot.service.trading;

import com.learn.springboot.domain.trading.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TradingService {
    private final StockInfoRepository stockInfoRepository;
    private final StockPriceRepository stockPriceRepository;
    private final MemberRepository memberRepository;
    private final HoldingStocksRepository holdingStocksRepository;
    private final TradingLogRepository tradingLogRepository;

    @Transactional
    public void buyStock(String companyCode, Long amount, String name){
        // 주식 구매
        // 종목코드와 매수량, 멤버 id를 입력받음
        // 받은 종목코드로 실시간 주가에 가서 price 받아옴
        // 매수량과 price로 가격 계산
        // 멤버 id로 멤버 정보를 조회해서 잔액과 비교
        // 가격보다 잔액이 크거나 같으면 체결가능
        // 체결가능할 경우에만 갖고있는 종목코드, 매수량, 멤버 id, 거래 가격을 db에 기록(거래번호(PK)와 체결시각(basetime)은 자동으로)
        // 멤버 정보에 가서 구매한 가격만큼 잔액 조정, 보유 현황에 가서 해당 주식 보유량 조정
        // 해당 주식이 이전까지 없었다면 보유 현황에 등록

        StockPrice stockPrice = stockPriceRepository.findStockPriceByCodeDesc(companyCode);
        StockInfo stockInfo = stockInfoRepository.findStockInfoByCodeDesc(companyCode);
        Member member = memberRepository.findMemberByNameDesc(name);


        Long moneyRequired = stockPrice.getRealTimePrice() * amount;
        if(!member.canAfford(moneyRequired)){
            return;
        }

        TradingLog tradingLog = tradingLogRepository.save(
                TradingLog.builder()
                        .isBuying(true)
                        .stockInfo(stockInfo)
                        .member(member)
                        .tradeAmount(amount)
                        .tradePrice(stockPrice.getRealTimePrice())
                        .build());

        member.buyingStock(moneyRequired);

        HoldingStocks holdingStocks = holdingStocksRepository.findMembersStock(name, companyCode)
                .map(entity -> entity.buyingStocks(amount)).orElse(
                        HoldingStocks.builder()
                                .stockInfo(stockInfo)
                                .member(member)
                                .shareAmount(amount)
                                .build()
                );
    }

    @Transactional
    public void sellStock(){
        // 주식 판매
        // 종목코드와 매도량, 멤버 id를 입력받음
        // 종목코드와 멤버 id로 보유현황 테이블 조회
        // 보유량과 매도량을 비교해서 보유량이 더 많거나 같으면 체결가능
        // 체결가능할 경우에만
        // 갖고있는 종목코드, 매도량, 멤버 id, 거래 가격을 db에 기록(거래번호(PK)와 체결시각(basetime)은 자동으로)
        // 종목코드로 price를 받아와서 매도량과 곱해 가격 계산
        // 멤버 정보에 가서 잔액 조정, 보유 현황에 가서 주식 보유량 조정
        // 매도를 통해 해당 주식의 보유량이 0이 될 경우 보유 현황에서 해당 주식 삭제
    }

    @Transactional
    public void deleteStock(){
        // 보유량이 0이 된 주식에 대한 record를 삭제(안하면 한 명당 200개의 빈 record가 남을 수도 있음)
    }
}
