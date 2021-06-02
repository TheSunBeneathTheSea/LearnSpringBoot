package com.learn.springboot.domain.trading;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class StockPrice {

    @OneToOne
    @JoinColumn(name = "companyCode")
    @Id
    private StockInfo stockInfo;

    // 종가
    @Column
    private Long clsPrice;

    // 실시간 주가
    @Column
    private Long realTimePrice;


    @Builder
    public StockPrice(StockInfo stockInfo, Long clsPrice, Long realTimePrice){
        this.stockInfo = stockInfo;
        this.clsPrice = clsPrice;
        this.realTimePrice = realTimePrice;
    }

    public void closeMarket(Long realTimePrice){
        this.clsPrice = realTimePrice;
        this.realTimePrice = realTimePrice;
    }

}

