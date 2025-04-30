package com.a2m.profileservice.exception;

import com.a2m.profileservice.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException ex) {
        return ResponseEntity.status(ex.getErrorCode().getStatus())
                .body(ApiResponse.builder()
                        .code(ex.getErrorCode().getCode())
                        .message(ex.getErrorCode().getMessage())
                        .build());
    }

    // Optionally handle other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex) {
        return ResponseEntity.status(500)
                .body(ApiResponse.builder()
                        .code(500)
                        .message("Internal Server Error")
                        .build());
    }
}

