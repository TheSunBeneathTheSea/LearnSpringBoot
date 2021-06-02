package com.learn.springboot.domain.trading;

import com.learn.springboot.domain.BaseTimeEntity;
import jdk.vm.ci.meta.Local;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class TradingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "companyCode")
    private StockInfo stockInfo;

    @ManyToOne
    @JoinColumn(name = "memberName")
    private Member member;

    @Column
    private Long tradePrice;

    @Column
    private Long tradeAmount;

    @Column
    private boolean isBuying;

    @CreatedDate
    @Column
    private LocalDateTime createdTime;

    @Builder
    public TradingLog(StockInfo stockInfo, Member member, Long tradePrice, Long tradeAmount, boolean isBuying){
        this.stockInfo = stockInfo;
        this.member = member;
        this.tradePrice = tradePrice;
        this.tradeAmount = tradeAmount;
        this.isBuying = isBuying;
    }

}
