package com.learn.springboot.domain.trading;

import com.learn.springboot.domain.trading.dto.CompanyCodeAndNameDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockInfoRepository extends JpaRepository<StockInfo, String> {

    @Query("SELECT p FROM StockInfo p ORDER BY p.companyCode DESC")
    List<StockInfo> findAllDesc();

    @Query("SELECT p FROM StockInfo p where companyCode = ?1 ORDER BY p.companyName DESC")
    StockInfo findStockInfoByCodeDesc(String companyCode);

    @Query("SELECT new com.learn.springboot.domain.trading.dto.CompanyCodeAndNameDto(companyCode, companyName) FROM StockInfo ORDER BY companyCode DESC")
    List<CompanyCodeAndNameDto> findAllCompanyCodeAndName();
}
