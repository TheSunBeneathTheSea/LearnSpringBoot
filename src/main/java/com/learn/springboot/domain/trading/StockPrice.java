package com.learn.springboot.domain.trading;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class StockPrice {

    @Id
    private String companyCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @MapsId
    private StockInfo stockInfo;

    // 종가
    @Column
    private Long clsPrice;

    // 실시간 주가
    @Column
    private Long realTimePrice;


    @Builder
    public StockPrice(StockInfo stockInfo, Long realTimePrice){
        this.companyCode = stockInfo.getCompanyCode();
        this.stockInfo = stockInfo;
        this.realTimePrice = realTimePrice;
    }

    public void closeMarket(){
        this.clsPrice = this.realTimePrice;
    }
}

