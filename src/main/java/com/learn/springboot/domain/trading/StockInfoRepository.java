package com.learn.springboot.domain.trading;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockInfoRepository extends JpaRepository<StockInfo, String> {

    @Query("SELECT p FROM StockInfo p ORDER BY p.companyCode DESC")
    List<StockPrice> findAllDesc();

    @Query("SELECT p FROM StockInfo p where companyCode = ?1 ORDER BY p.companyName DESC")
    List<HoldingStocks> findStockInfoByCodeDesc(String companyCode);
}
