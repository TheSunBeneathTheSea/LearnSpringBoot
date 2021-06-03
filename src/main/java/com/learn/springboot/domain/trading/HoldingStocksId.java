package com.learn.springboot.domain.trading;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Embeddable
public class HoldingStocksId implements Serializable {
    private String memberName;
    private String companyCode;

    public HoldingStocksId(Member member, StockInfo stockInfo){
        this.memberName = member.getName();
        this.companyCode = stockInfo.getCompanyCode();
    }
}
