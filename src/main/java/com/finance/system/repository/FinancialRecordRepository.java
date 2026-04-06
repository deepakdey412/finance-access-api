package com.finance.system.repository;

import com.finance.system.entity.FinancialRecord;
import com.finance.system.entity.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
    
    // Filtering methods
    List<FinancialRecord> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<FinancialRecord> findByType(TransactionType type);
    
    List<FinancialRecord> findByCategory(String category);
    
    // Paginated filtering with dynamic query
    @Query("SELECT f FROM FinancialRecord f WHERE " +
           "(:startDate IS NULL OR f.date >= :startDate) AND " +
           "(:endDate IS NULL OR f.date <= :endDate) AND " +
           "(:type IS NULL OR f.type = :type) AND " +
           "(:category IS NULL OR f.category = :category) AND " +
           "f.isDeleted = false")
    Page<FinancialRecord> findByFilters(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("type") TransactionType type,
        @Param("category") String category,
        Pageable pageable
    );
    
    // Dashboard queries
    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.type = :type AND f.isDeleted = false")
    BigDecimal sumByType(@Param("type") TransactionType type);
    
    @Query("SELECT f.category, SUM(f.amount) FROM FinancialRecord f WHERE f.isDeleted = false GROUP BY f.category")
    List<Object[]> sumByCategory();
    
    @Query("SELECT YEAR(f.date), MONTH(f.date), SUM(f.amount) FROM FinancialRecord f " +
           "WHERE f.isDeleted = false " +
           "GROUP BY YEAR(f.date), MONTH(f.date) " +
           "ORDER BY YEAR(f.date), MONTH(f.date)")
    List<Object[]> monthlyTrends();
    
    List<FinancialRecord> findTop10ByIsDeletedFalseOrderByDateDesc();
}
