package com.example.vendingmachine.exception;

import com.example.vendingmachine.model.ServiceErrorResponse;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;

public enum ServiceErrorCode {

    INTERNAL_SERVICE_ERROR("001", HttpStatus.INTERNAL_SERVER_ERROR, ServiceErrorResponse.ServiceCodeEnum.CODE001),
    BAD_REQUEST("002", HttpStatus.BAD_REQUEST, ServiceErrorResponse.ServiceCodeEnum.CODE002);

    public final String label;
    public final HttpStatus associatedStatus;
    public final ServiceErrorResponse.ServiceCodeEnum associatedServiceCodeEnum;

    ServiceErrorCode(String label, HttpStatus associatedStatus, ServiceErrorResponse.ServiceCodeEnum associatedServiceCodeEnum) {
        this.label = label;
        this.associatedStatus = associatedStatus;
        this.associatedServiceCodeEnum = associatedServiceCodeEnum;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    public HttpStatus getAssociatedStatus() {
        return associatedStatus;
    }
}
