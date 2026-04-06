package com.finance.system.dto.request;

import com.finance.system.entity.TransactionType;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFinancialRecordRequest {
    
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    private TransactionType type;
    
    private String category;
    
    private LocalDate date;
    
    private String description;
}
