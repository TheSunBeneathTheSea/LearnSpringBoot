package com.learn.springboot.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.springboot.domain.trading.dto.StockUpdateRequestDto;
import com.learn.springboot.service.trading.TradingService;
import com.learn.springboot.web.dto.StockTradeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
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

    @GetMapping("/api/v1/trading/update")
    public String updateStock() {
        ObjectMapper objectMapper = new ObjectMapper();

        String path = "classpath:data/price_now.json";

        Resource rr = new ClassPathResource("static/data/price_now.json");

        try{
            File temp = rr.getFile();

            List<StockUpdateRequestDto> updateRequestDtoList
                    = objectMapper.readValue(rr.getFile(), new TypeReference<List<StockUpdateRequestDto>>() {
            });
            return tradingService.updateStock(updateRequestDtoList);

        }catch (IOException e){
            e.printStackTrace();
        }


        return "wow";
    }
}
