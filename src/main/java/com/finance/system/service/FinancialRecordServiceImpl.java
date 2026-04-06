package com.finance.system.service;

import com.finance.system.dto.request.CreateFinancialRecordRequest;
import com.finance.system.dto.request.FinancialRecordFilterRequest;
import com.finance.system.dto.request.UpdateFinancialRecordRequest;
import com.finance.system.dto.response.FinancialRecordResponse;
import com.finance.system.entity.FinancialRecord;
import com.finance.system.exception.FinancialRecordNotFoundException;
import com.finance.system.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinancialRecordServiceImpl implements FinancialRecordService {
    
    private final FinancialRecordRepository recordRepository;
    
    @Override
    @Transactional
    public FinancialRecordResponse createRecord(CreateFinancialRecordRequest request) {
        FinancialRecord record = new FinancialRecord();
        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setDescription(request.getDescription());
        record.setIsDeleted(false);
        
        FinancialRecord savedRecord = recordRepository.save(record);
        return mapToResponse(savedRecord);
    }
    
    @Override
    @Transactional
    public FinancialRecordResponse updateRecord(Long id, UpdateFinancialRecordRequest request) {
        FinancialRecord record = recordRepository.findById(id)
            .orElseThrow(() -> new FinancialRecordNotFoundException("Financial record not found"));
        
        if (request.getAmount() != null) {
            record.setAmount(request.getAmount());
        }
        if (request.getType() != null) {
            record.setType(request.getType());
        }
        if (request.getCategory() != null) {
            record.setCategory(request.getCategory());
        }
        if (request.getDate() != null) {
            record.setDate(request.getDate());
        }
        if (request.getDescription() != null) {
            record.setDescription(request.getDescription());
        }
        
        FinancialRecord updatedRecord = recordRepository.save(record);
        return mapToResponse(updatedRecord);
    }
    
    @Override
    @Transactional
    public void deleteRecord(Long id) {
        FinancialRecord record = recordRepository.findById(id)
            .orElseThrow(() -> new FinancialRecordNotFoundException("Financial record not found"));
        
        // Soft delete
        record.setIsDeleted(true);
        recordRepository.save(record);
    }
    
    @Override
    @Transactional(readOnly = true)
    public FinancialRecordResponse getRecordById(Long id) {
        FinancialRecord record = recordRepository.findById(id)
            .orElseThrow(() -> new FinancialRecordNotFoundException("Financial record not found"));
        return mapToResponse(record);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<FinancialRecordResponse> getAllRecords() {
        return recordRepository.findAll().stream()
            .filter(record -> !record.getIsDeleted())
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<FinancialRecordResponse> getFilteredRecords(FinancialRecordFilterRequest filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        
        Page<FinancialRecord> records = recordRepository.findByFilters(
            filter.getStartDate(),
            filter.getEndDate(),
            filter.getType(),
            filter.getCategory(),
            pageable
        );
        
        return records.map(this::mapToResponse);
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
