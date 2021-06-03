package com.learn.springboot.domain.trading;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class HoldingStocks {

    @EmbeddedId
    private HoldingStocksId id = new HoldingStocksId();

    @MapsId("memberName")
    @ManyToOne(cascade = CascadeType.ALL)
    private Member member;

    @MapsId("companyCode")
    @ManyToOne(cascade = CascadeType.ALL)
    private StockInfo stockInfo;

    @Column
    private Long shareAmount;

    @Builder
    public HoldingStocks(Member member, StockInfo stockInfo, Long shareAmount){
        this.member = member;
        this.stockInfo = stockInfo;
        this.shareAmount = shareAmount;
    }

    public HoldingStocks buyingStocks(Long amount){
        this.shareAmount += amount;
        return this;
    }

    public HoldingStocks sellingStocks(Long amount){
        this.shareAmount -= amount;
        return this;
    }
}

