package com.learn.springboot.domain.trading;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TradingLogRepository extends JpaRepository<TradingLog, Long> {

    @Query("SELECT p FROM TradingLog p ORDER BY p.id DESC")
    List<TradingLog> findAllDesc();

    @Query("SELECT p FROM TradingLog p where id = ?1 ORDER BY p.id DESC")
    List<TradingLog> findByIdDesc(Long id);

    @Query("SELECT p FROM TradingLog p where memberName = ?1 ORDER BY p.id DESC")
    List<TradingLog> findByNameDesc(String name);

    @Query("SELECT p FROM TradingLog p where companyCode = ?1 ORDER BY p.id DESC")
    List<TradingLog> findByStockDesc(String code);
}
