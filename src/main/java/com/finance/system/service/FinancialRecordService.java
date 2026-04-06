package com.finance.system.service;

import com.finance.system.dto.request.CreateFinancialRecordRequest;
import com.finance.system.dto.request.FinancialRecordFilterRequest;
import com.finance.system.dto.request.UpdateFinancialRecordRequest;
import com.finance.system.dto.response.FinancialRecordResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FinancialRecordService {
    
    FinancialRecordResponse createRecord(CreateFinancialRecordRequest request);
    
    FinancialRecordResponse updateRecord(Long id, UpdateFinancialRecordRequest request);
    
    void deleteRecord(Long id);
    
    FinancialRecordResponse getRecordById(Long id);
    
    List<FinancialRecordResponse> getAllRecords();
    
    Page<FinancialRecordResponse> getFilteredRecords(FinancialRecordFilterRequest filter);
}
