package com.finance.system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTrend {
    
    private Integer year;
    
    private Integer month;
    
    private BigDecimal amount;
}
