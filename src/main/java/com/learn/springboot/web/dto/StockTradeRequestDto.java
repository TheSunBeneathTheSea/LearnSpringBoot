package com.learn.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StockTradeRequestDto {
    private String companyCode;
    private Long amount;
    private String name;

    @Builder
    public StockTradeRequestDto(String companyCode, Long amount, String name){
        this.companyCode = companyCode;
        this.amount = amount;
        this.name = name;
    }
}
