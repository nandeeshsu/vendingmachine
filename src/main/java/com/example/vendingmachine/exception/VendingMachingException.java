package com.example.vendingmachine.exception;

import org.springframework.http.HttpStatus;

public class VendingMachingException extends RuntimeException {

    private final ServiceErrorCode serviceErrorCode;

    public VendingMachingException(String message, ServiceErrorCode serviceErrorCode) {
        super(message);
        this.serviceErrorCode = serviceErrorCode;
    }

    public ServiceErrorCode getServiceErrorCode() {
        return this.serviceErrorCode;
    }

    public HttpStatus getHttpStatus() {
        return serviceErrorCode.getAssociatedStatus();
    }
}
