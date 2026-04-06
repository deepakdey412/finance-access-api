package com.finance.system.dto.request;

import com.finance.system.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialRecordFilterRequest {
    
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private TransactionType type;
    
    private String category;
    
    private Integer page = 0;
    
    private Integer size = 20;
}
