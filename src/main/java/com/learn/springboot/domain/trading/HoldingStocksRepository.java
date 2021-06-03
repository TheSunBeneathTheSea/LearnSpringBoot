package com.learn.springboot.domain.trading;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HoldingStocksRepository extends JpaRepository<HoldingStocks, HoldingStocksId>{

    @Query("SELECT p FROM HoldingStocks p ORDER BY member_name DESC")
    List<HoldingStocks> findAllDesc();

    @Query("SELECT p FROM HoldingStocks p where member_name = ?1 ORDER BY stock_info_company_code DESC")
    List<HoldingStocks> findByMemberName(String memberName);

    @Query("SELECT p FROM HoldingStocks p where stock_info_company_code = ?1 ORDER BY stock_info_company_code DESC")
    List<HoldingStocks> findByCompanyCode(String companyCode);

    @Query("SELECT p FROM HoldingStocks p where member_name = ?1 and stock_info_company_code = ?2 ORDER BY stock_info_company_code DESC")
    Optional<HoldingStocks> findMembersStock(String memberName, String companyCode);
}
