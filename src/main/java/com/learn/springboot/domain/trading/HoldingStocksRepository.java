package com.learn.springboot.domain.trading;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HoldingStocksRepository extends JpaRepository<HoldingStocks, String>{

    @Query("SELECT p FROM HoldingStocks p ORDER BY p.memberName DESC")
    List<HoldingStocks> findAllDesc();

    @Query("SELECT p FROM HoldingStocks p where memberName = ?1 ORDER BY p.memberName DESC")
    List<HoldingStocks> findAccountDesc(String memberName);
}
