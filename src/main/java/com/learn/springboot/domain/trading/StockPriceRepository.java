package com.learn.springboot.domain.trading;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockPriceRepository extends JpaRepository<StockPrice, String> {

    @Query("SELECT p FROM StockPrice p ORDER BY p.companyCode DESC")
    List<StockPrice> findAllDesc();

    @Query("SELECT p FROM StockPrice p ORDER BY p.companyCode ASC")
    List<StockPrice> findAllAsc();

    @Query("SELECT p FROM StockPrice p where companyCode = ?1 ORDER BY companyCode DESC")
    StockPrice findStockPriceByCodeDesc(String companyCode);
}
