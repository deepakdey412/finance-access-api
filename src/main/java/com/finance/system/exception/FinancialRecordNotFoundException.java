package com.finance.system.exception;

public class FinancialRecordNotFoundException extends RuntimeException {
    public FinancialRecordNotFoundException(String message) {
        super(message);
    }
}
