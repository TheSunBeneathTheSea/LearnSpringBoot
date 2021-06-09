package com.learn.springboot.domain.trading.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StockUpdateRequestDto {
    private String companyCode;
    private String companyName;
    private String industry;
    private Long price;

    @Builder
    public StockUpdateRequestDto(String companyCode, String companyName, String industry, Long price){
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.industry = industry;
        this.price = price;
    }
}
