package com.learn.springboot.domain.trading;

import com.learn.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    private String name;

    @OneToOne
    @JoinColumn(name = "userId")
    @Column
    private User user;
    
    @Column
    private Long accountBalance;

    @OneToMany(mappedBy = "member")
    private List<HoldingStocks> holdingStocksList;

    @Builder
    public Member(String name, User user, Long accountBalance){
        this.name = name;
        this.user = user;
        this.accountBalance = accountBalance;
    }

}
