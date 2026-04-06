package com.finance.system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryResponse {
    
    private BigDecimal totalIncome;
    
    private BigDecimal totalExpense;
    
    private BigDecimal netBalance;
    
    private Map<String, BigDecimal> categoryTotals;
    
    private List<MonthlyTrend> monthlyTrends;
    
    private List<FinancialRecordResponse> recentTransactions;
}
