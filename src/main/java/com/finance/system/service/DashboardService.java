package com.finance.system.service;

import com.finance.system.dto.response.DashboardSummaryResponse;
import com.finance.system.dto.response.FinancialRecordResponse;
import com.finance.system.dto.response.MonthlyTrend;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DashboardService {
    
    DashboardSummaryResponse getDashboardSummary();
    
    BigDecimal getTotalIncome();
    
    BigDecimal getTotalExpense();
    
    BigDecimal getNetBalance();
    
    Map<String, BigDecimal> getCategoryTotals();
    
    List<MonthlyTrend> getMonthlyTrends();
    
    List<FinancialRecordResponse> getRecentTransactions();
}
