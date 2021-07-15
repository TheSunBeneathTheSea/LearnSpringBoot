package com.learn.springboot.domain.member;

import com.learn.springboot.domain.trading.HoldingStocks;
import com.learn.springboot.domain.trading.StockInfo;
import com.learn.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    @Column
    private Long accountBalance;

    @ManyToMany
    @JoinTable(
            name = "targetStocks",
            joinColumns = @JoinColumn(name = "member_name"),
            inverseJoinColumns = @JoinColumn(name = "stockInfo_companyCode")
    )
    Set<StockInfo> targetStocks;

    @OneToMany(mappedBy = "member")
    private List<HoldingStocks> holdingStocksList;

    @Builder
    public Member(String name, User user, Long accountBalance){
        this.name = name;
        this.user = user;
        this.accountBalance = accountBalance;
    }

    public boolean canAfford(Long moneyRequired){
        return moneyRequired < accountBalance;
    }

    public void buyingStock(Long payment){
        this.accountBalance -= payment;
    }

    public void sellingStock(Long profit){
        this.accountBalance += profit;
    }

    public void updateTarget(Set<StockInfo> targetStocks){
        this.targetStocks = targetStocks;
    }

    public void setAccountBalance(Long amount){
        this.accountBalance += amount;
    }
}
