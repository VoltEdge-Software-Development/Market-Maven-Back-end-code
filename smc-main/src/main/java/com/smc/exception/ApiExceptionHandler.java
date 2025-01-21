package com.smc.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    private static final String FAILED_RESPONSE = "FAILED";

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleRequestException(ApiRequestException e){
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(e.getMessage(),FAILED_RESPONSE);
        return ResponseEntity.ok(apiExceptionResponse);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleBadRequestException(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.put("message",errorMessage);
        });
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(errors.get("message"),FAILED_RESPONSE);
        return ResponseEntity.ok(apiExceptionResponse);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMismatchType(MethodArgumentTypeMismatchException e) {
        String name = e.getName();
        String type = Objects.requireNonNull(e.getRequiredType()).getSimpleName();
        Object value = e.getValue();
        String message = String.format("%s should be a valid '%s' and you provided '%s'", name, type, value);
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(message,FAILED_RESPONSE);
        return ResponseEntity.ok(apiExceptionResponse);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<Object> handleTransactionException(TransactionException e){
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(e.getMessage(),FAILED_RESPONSE);
        return ResponseEntity.ok(apiExceptionResponse);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleRequestException(Exception e){
        log.error(e.getMessage()+e.getCause()+e.getLocalizedMessage()+e.fillInStackTrace());
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse("An error occurred", FAILED_RESPONSE);
        return ResponseEntity.ok(apiExceptionResponse);
    }

}
