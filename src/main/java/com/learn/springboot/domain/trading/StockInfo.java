package com.learn.springboot.domain.trading;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class StockInfo {

    @Id
    private String companyCode;

    @Column
    private String companyName;

    @Column
    private String industry;

    @Builder
    public StockInfo(String companyCode, String companyName, String industry){
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.industry = industry;
    }
}
