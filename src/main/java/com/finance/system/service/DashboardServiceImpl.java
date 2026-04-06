package com.finance.system.service;

import com.finance.system.dto.response.DashboardSummaryResponse;
import com.finance.system.dto.response.FinancialRecordResponse;
import com.finance.system.dto.response.MonthlyTrend;
import com.finance.system.entity.FinancialRecord;
import com.finance.system.entity.TransactionType;
import com.finance.system.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    
    private final FinancialRecordRepository recordRepository;
    
    @Override
    @Transactional(readOnly = true)
    public DashboardSummaryResponse getDashboardSummary() {
        DashboardSummaryResponse summary = new DashboardSummaryResponse();
        summary.setTotalIncome(getTotalIncome());
        summary.setTotalExpense(getTotalExpense());
        summary.setNetBalance(getNetBalance());
        summary.setCategoryTotals(getCategoryTotals());
        summary.setMonthlyTrends(getMonthlyTrends());
        summary.setRecentTransactions(getRecentTransactions());
        return summary;
    }
    
    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalIncome() {
        BigDecimal total = recordRepository.sumByType(TransactionType.INCOME);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalExpense() {
        BigDecimal total = recordRepository.sumByType(TransactionType.EXPENSE);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    @Override
    @Transactional(readOnly = true)
    public BigDecimal getNetBalance() {
        return getTotalIncome().subtract(getTotalExpense());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Map<String, BigDecimal> getCategoryTotals() {
        List<Object[]> results = recordRepository.sumByCategory();
        return results.stream()
            .collect(Collectors.toMap(
                row -> (String) row[0],
                row -> (BigDecimal) row[1]
            ));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MonthlyTrend> getMonthlyTrends() {
        List<Object[]> results = recordRepository.monthlyTrends();
        return results.stream()
            .map(row -> new MonthlyTrend(
                (Integer) row[0],
                (Integer) row[1],
                (BigDecimal) row[2]
            ))
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<FinancialRecordResponse> getRecentTransactions() {
        List<FinancialRecord> records = recordRepository.findTop10ByIsDeletedFalseOrderByDateDesc();
        return records.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }
    
    private FinancialRecordResponse mapToResponse(FinancialRecord record) {
        return new FinancialRecordResponse(
            record.getId(),
            record.getAmount(),
            record.getType(),
            record.getCategory(),
            record.getDate(),
            record.getDescription(),
            record.getCreatedAt(),
            record.getUpdatedAt()
        );
    }
}
