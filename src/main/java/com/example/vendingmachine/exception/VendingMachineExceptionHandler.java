package com.example.vendingmachine.exception;

import com.example.vendingmachine.model.ServiceErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class VendingMachineExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(makeErrorResponse(e, ServiceErrorCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(makeErrorResponse(e, ServiceErrorCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(makeErrorResponse(e, ServiceErrorCode.BAD_REQUEST), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(makeErrorResponse(e, ServiceErrorCode.BAD_REQUEST), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(makeErrorResponse(e, ServiceErrorCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(makeErrorResponse(e, ServiceErrorCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class, ResponseStatusException.class})
    public ResponseEntity<ServiceErrorResponse> handleAnyRuntimeException(Exception e, WebRequest request) {
        return new ResponseEntity<>(makeErrorResponse(e, ServiceErrorCode.INTERNAL_SERVICE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(VendingMachingException.class)
    public ResponseEntity<ServiceErrorResponse> handleVendingMachineException(VendingMachingException e) {
        return new ResponseEntity<>(makeErrorResponse(e), e.getHttpStatus());
    }

    private ServiceErrorResponse makeErrorResponse(Exception e, ServiceErrorCode serviceErrorCode) {
        return new ServiceErrorResponse()
                .message(String.format("Exception Message received: '%s'", e.getMessage()))
                .serviceCode(serviceErrorCode.associatedServiceCodeEnum)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    private ServiceErrorResponse makeErrorResponse(VendingMachingException e) {
        return new ServiceErrorResponse()
                .message(e.getMessage())
                .serviceCode(e.getServiceErrorCode().associatedServiceCodeEnum)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}

