package com.learn.springboot.domain.trading.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanyCodeAndNameDto {
    public String companyCode;
    public String companyName;

    @Builder
    public CompanyCodeAndNameDto(String companyCode, String companyName){
        this.companyCode = companyCode;
        this.companyName = companyName;
    }
}
