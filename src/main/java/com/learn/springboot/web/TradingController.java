package com.learn.springboot.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.springboot.domain.trading.dto.StockUpdateRequestDto;
import com.learn.springboot.service.trading.TradingService;
import com.learn.springboot.web.dto.StockTradeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TradingController {

    private final TradingService tradingService;

    @PostMapping("/api/v1/trading/buy")
    public String buy(@RequestBody StockTradeRequestDto stockTradeRequestDto){
        return tradingService.buyStock(stockTradeRequestDto);
    }

    @PostMapping("/api/v1/trading/sell")
    public String sell(@RequestBody StockTradeRequestDto stockTradeRequestDto){
        return tradingService.sellStock(stockTradeRequestDto);
    }

    @Schedules({
            @Scheduled(cron = "0 0/5 9-14 ? * 2-6"),
            @Scheduled(cron = "0 0-25/5 15 ? * 2-6")
    })
    public String updateStock() {
        String updateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 M월 dd일 HH:mm"));

        ObjectMapper objectMapper = new ObjectMapper();

        //String path = "classpath:data/price_now.json";

        //Resource rr = new ClassPathResource("data/price_now.json");

        //runtime에 외부 파일을 읽기 위해 절대경로로 교체

        String absPath = "C:/Users/USER/IdeaProjects/LearnSpringBoot/src/main/resources/data/price_now.json";

        try{
            File temp = new File(absPath);

            List<StockUpdateRequestDto> updateRequestDtoList
                    = objectMapper.readValue(temp, new TypeReference<List<StockUpdateRequestDto>>() {
            });
            tradingService.updateStock(updateRequestDtoList);

            return updateTime;

        }catch (IOException e){
            e.printStackTrace();
        }

        return "update failed";
    }

    @Scheduled(cron = "0 30 15 ? * 2-6")
    public String closeMarket(){
        return tradingService.closeMarket();
    }
}
