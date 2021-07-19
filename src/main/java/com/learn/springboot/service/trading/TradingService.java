package com.learn.springboot.service.trading;

import com.learn.springboot.domain.member.Member;
import com.learn.springboot.domain.member.MemberRepository;
import com.learn.springboot.domain.trading.*;
import com.learn.springboot.domain.trading.dto.CompanyCodeAndNameDto;
import com.learn.springboot.domain.trading.dto.StockUpdateRequestDto;
import com.learn.springboot.web.dto.StockTradeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class TradingService {
    private final StockInfoRepository stockInfoRepository;
    private final StockPriceRepository stockPriceRepository;
    private final MemberRepository memberRepository;
    private final HoldingStocksRepository holdingStocksRepository;
    private final TradingLogRepository tradingLogRepository;

    @Transactional
    public String buyStock(StockTradeRequestDto stockTradeRequestDto){
        // 주식 구매
        // 종목코드와 매수량, 멤버 id를 입력받음
        // 받은 종목코드로 실시간 주가에 가서 price 받아옴
        // 매수량과 price로 가격 계산
        // 멤버 id로 멤버 정보를 조회해서 잔액과 비교
        // 가격보다 잔액이 크거나 같으면 체결가능
        // 체결가능할 경우에만 갖고있는 종목코드, 매수량, 멤버 id, 거래 가격을 db에 기록(거래번호(PK)와 체결시각(basetime)은 자동으로)
        // 멤버 정보에 가서 구매한 가격만큼 잔액 조정, 보유 현황에 가서 해당 주식 보유량 조정
        // 해당 주식이 이전까지 없었다면 보유 현황에 등록

        String companyCode = stockTradeRequestDto.getCompanyCode();
        Long amount = stockTradeRequestDto.getAmount();
        String name = stockTradeRequestDto.getName();

        StockPrice stockPrice = stockPriceRepository.findStockPriceByCodeDesc(companyCode);
        StockInfo stockInfo = stockInfoRepository.findStockInfoByCodeDesc(companyCode);
        Member member = memberRepository.findMemberByNameDesc(name);


        Long moneyRequired = stockPrice.getRealTimePrice() * amount;
        if(!member.canAfford(moneyRequired)){
            return "잔고가 부족합니다";
        }

        tradingLogRepository.save(
                TradingLog.builder()
                        .isBuying(true)
                        .stockInfo(stockInfo)
                        .member(member)
                        .tradeAmount(amount)
                        .tradePrice(stockPrice.getRealTimePrice())
                        .build());

        member.buyingStock(moneyRequired);

        holdingStocksRepository.findMembersStock(name, companyCode)
                .map(entity -> entity.buyingStocks(amount)).orElse(
                        holdingStocksRepository.save(HoldingStocks.builder()
                                .stockInfo(stockInfo)
                                .member(member)
                                .shareAmount(amount)
                                .build())
                );

        return "매수 성공";
    }

    @Transactional
    public String sellStock(StockTradeRequestDto stockTradeRequestDto){
        // 주식 판매
        // 종목코드와 매도량, 멤버 id를 입력받음
        // 종목코드와 멤버 id로 보유현황 테이블 조회
        // 보유량과 매도량을 비교해서 보유량이 더 많거나 같으면 체결가능
        // 체결가능할 경우에만
        // 갖고있는 종목코드, 매도량, 멤버 id, 거래 가격을 db에 기록(거래번호(PK)와 체결시각(basetime)은 자동으로)
        // 종목코드로 price를 받아와서 매도량과 곱해 가격 계산
        // 멤버 정보에 가서 잔액 조정, 보유 현황에 가서 주식 보유량 조정
        // 매도를 통해 해당 주식의 보유량이 0이 될 경우 보유 현황에서 해당 주식 삭제

        String companyCode = stockTradeRequestDto.getCompanyCode();
        Long amount = stockTradeRequestDto.getAmount();
        String name = stockTradeRequestDto.getName();

        StockPrice stockPrice = stockPriceRepository.findStockPriceByCodeDesc(companyCode);
        StockInfo stockInfo = stockInfoRepository.findStockInfoByCodeDesc(companyCode);
        Member member = memberRepository.findMemberByNameDesc(name);
        HoldingStocks holdingStocks = holdingStocksRepository.findMembersStock(name, companyCode)
                .orElseThrow(NoSuchElementException::new);

        if(!holdingStocks.holdEnoughStock(amount)){
            return "보유 주식이 부족합니다";
        }

        TradingLog tradingLog = tradingLogRepository.save(
                TradingLog.builder()
                        .isBuying(false)
                        .stockInfo(stockInfo)
                        .member(member)
                        .tradeAmount(amount)
                        .tradePrice(stockPrice.getRealTimePrice())
                        .build());

        Long profit = amount * stockPrice.getRealTimePrice();

        member.sellingStock(profit);

        holdingStocks.sellingStocks(amount);

        if(holdingStocks.getShareAmount() == 0){
            holdingStocksRepository.delete(holdingStocks);
        }

        return "매도 성공";
    }

    @Transactional
    public String updateStock(List<StockUpdateRequestDto> requestDtoList){
        if(stockInfoRepository.count() == 0){
            for (StockUpdateRequestDto requestDto: requestDtoList) {
                StockInfo stockInfo = StockInfo.builder()
                        .companyCode(requestDto.getCompanyCode())
                        .companyName(requestDto.getCompanyName())
                        .industry(requestDto.getIndustry())
                        .build();

                stockInfoRepository.save(stockInfo);

                stockPriceRepository.save(StockPrice.builder()
                        .stockInfo(stockInfo)
                        .realTimePrice(requestDto.getPrice())
                        .build()
                );
            }
        }else{
            List<StockInfo> stockInfoList = stockInfoRepository.findAll();

            for (StockUpdateRequestDto requestDto: requestDtoList) {
                StockInfo stockInfo = stockInfoRepository.findStockInfoByCodeDesc(requestDto.getCompanyCode());

                stockInfoList.remove(stockInfo);

                stockPriceRepository.save(StockPrice.builder()
                        .stockInfo(stockInfo)
                        .realTimePrice(requestDto.getPrice())
                        .build()
                );
            }
        }

        return "update success";
    }

    @Transactional
    public String closeMarket(){
        List<StockPrice> stockPriceList = stockPriceRepository.findAllDesc();

        for (StockPrice stockPrice: stockPriceList) {
            stockPrice.closeMarket();
        }
        return "Market has closed";
    }

    public List<StockPrice> findAllStockPrice(){
        return stockPriceRepository.findAllAsc();
    }

    @Transactional(readOnly = true)
    public List<CompanyCodeAndNameDto> findAllCompanyCodeAndName(){
        List<CompanyCodeAndNameDto> companyCodeAndName = new ArrayList<>();

        companyCodeAndName = stockInfoRepository.findAllCompanyCodeAndName();

        return companyCodeAndName;
    }
}
