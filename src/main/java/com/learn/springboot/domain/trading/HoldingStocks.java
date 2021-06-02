package com.learn.springboot.domain.trading;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
@NoArgsConstructor
@IdClass(HoldingStocksId.class)
public class HoldingStocks {

    @Id
    @ManyToOne
    @JoinColumn(name = "memberName")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name = "companyCode")
    private StockInfo stockInfo;

    @Column
    private Long shareAmount;

    @Builder
    public HoldingStocks(Member member, StockInfo stockInfo, Long shareAmount){
        this.member = member;
        this.stockInfo = stockInfo;
        this.shareAmount = shareAmount;
    }
}

@NoArgsConstructor
class HoldingStocksId implements Serializable {
    private String memberName;
    private String companyCode;

    public HoldingStocksId(String memberName, String companyCode){
        this.memberName = memberName;
        this.companyCode = companyCode;
    }
}