package com.finance.system.controller;

import com.finance.system.dto.request.CreateFinancialRecordRequest;
import com.finance.system.dto.request.FinancialRecordFilterRequest;
import com.finance.system.dto.request.UpdateFinancialRecordRequest;
import com.finance.system.dto.response.FinancialRecordResponse;
import com.finance.system.service.FinancialRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
@Tag(name = "Financial Records", description = "APIs for managing financial records")
@SecurityRequirement(name = "bearerAuth")
public class FinancialRecordController {
    
    private final FinancialRecordService recordService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create financial record", description = "Create a new financial record. Only accessible by ADMIN.")
    public ResponseEntity<FinancialRecordResponse> createRecord(
        @Valid @RequestBody CreateFinancialRecordRequest request
    ) {
        FinancialRecordResponse response = recordService.createRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update financial record", description = "Update an existing financial record. Only accessible by ADMIN.")
    public ResponseEntity<FinancialRecordResponse> updateRecord(
        @PathVariable Long id,
        @Valid @RequestBody UpdateFinancialRecordRequest request
    ) {
        FinancialRecordResponse response = recordService.updateRecord(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete financial record", description = "Delete a financial record (soft delete). Only accessible by ADMIN.")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('VIEWER', 'ANALYST', 'ADMIN')")
    @Operation(summary = "Get financial record by ID", description = "Retrieve a specific financial record. Accessible by VIEWER, ANALYST, and ADMIN.")
    public ResponseEntity<FinancialRecordResponse> getRecordById(@PathVariable Long id) {
        FinancialRecordResponse response = recordService.getRecordById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('VIEWER', 'ANALYST', 'ADMIN')")
    @Operation(summary = "Get filtered financial records", description = "Retrieve financial records with optional filters (date range, type, category) and pagination. Accessible by VIEWER, ANALYST, and ADMIN.")
    public ResponseEntity<Page<FinancialRecordResponse>> getFilteredRecords(
        @ModelAttribute FinancialRecordFilterRequest filter
    ) {
        Page<FinancialRecordResponse> records = recordService.getFilteredRecords(filter);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('VIEWER', 'ANALYST', 'ADMIN')")
    @Operation(summary = "Get all financial records (non-paginated)", description = "Retrieve all financial records without pagination. Accessible by VIEWER, ANALYST, and ADMIN.")
    public ResponseEntity<List<FinancialRecordResponse>> getAllRecords() {
        List<FinancialRecordResponse> records = recordService.getAllRecords();
        return ResponseEntity.ok(records);
    }
}
