package com.finance.system.controller;

import com.finance.system.dto.response.DashboardSummaryResponse;
import com.finance.system.dto.response.FinancialRecordResponse;
import com.finance.system.dto.response.MonthlyTrend;
import com.finance.system.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard Analytics", description = "APIs for financial analytics and summaries (ANALYST and ADMIN only)")
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {
    
    private final DashboardService dashboardService;
    
    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    @Operation(summary = "Get dashboard summary", description = "Retrieve complete dashboard summary with all analytics. Accessible by ANALYST and ADMIN.")
    public ResponseEntity<DashboardSummaryResponse> getDashboardSummary() {
        DashboardSummaryResponse summary = dashboardService.getDashboardSummary();
        return ResponseEntity.ok(summary);
    }
    
    @GetMapping("/income")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    @Operation(summary = "Get total income", description = "Calculate and return total income. Accessible by ANALYST and ADMIN.")
    public ResponseEntity<BigDecimal> getTotalIncome() {
        BigDecimal total = dashboardService.getTotalIncome();
        return ResponseEntity.ok(total);
    }
    
    @GetMapping("/expense")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    @Operation(summary = "Get total expense", description = "Calculate and return total expense. Accessible by ANALYST and ADMIN.")
    public ResponseEntity<BigDecimal> getTotalExpense() {
        BigDecimal total = dashboardService.getTotalExpense();
        return ResponseEntity.ok(total);
    }
    
    @GetMapping("/balance")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    @Operation(summary = "Get net balance", description = "Calculate and return net balance (income - expense). Accessible by ANALYST and ADMIN.")
    public ResponseEntity<BigDecimal> getNetBalance() {
        BigDecimal balance = dashboardService.getNetBalance();
        return ResponseEntity.ok(balance);
    }
    
    @GetMapping("/category-totals")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    @Operation(summary = "Get category-wise totals", description = "Retrieve aggregated amounts grouped by category. Accessible by ANALYST and ADMIN.")
    public ResponseEntity<Map<String, BigDecimal>> getCategoryTotals() {
        Map<String, BigDecimal> totals = dashboardService.getCategoryTotals();
        return ResponseEntity.ok(totals);
    }
    
    @GetMapping("/monthly-trends")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    @Operation(summary = "Get monthly trends", description = "Retrieve aggregated amounts grouped by month. Accessible by ANALYST and ADMIN.")
    public ResponseEntity<List<MonthlyTrend>> getMonthlyTrends() {
        List<MonthlyTrend> trends = dashboardService.getMonthlyTrends();
        return ResponseEntity.ok(trends);
    }
    
    @GetMapping("/recent")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    @Operation(summary = "Get recent transactions", description = "Retrieve the 10 most recent financial records. Accessible by ANALYST and ADMIN.")
    public ResponseEntity<List<FinancialRecordResponse>> getRecentTransactions() {
        List<FinancialRecordResponse> recent = dashboardService.getRecentTransactions();
        return ResponseEntity.ok(recent);
    }
}
