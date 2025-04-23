package com.a2m.profileservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 1xxx – Auth
    BUSINESS_EXISTED(1001, "Business already exists", HttpStatus.BAD_REQUEST),
    BUSINESS_NOT_FOUND(1002, "Business not found", HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS(1003, "Username or password is incorrect", HttpStatus.UNAUTHORIZED),

    // 2xxx – Validation
    VALIDATION_ERROR(2001, "Invalid input: {field}", HttpStatus.BAD_REQUEST),

    // 3xxx – Business Logic
    OUT_OF_STOCK(3001, "Product is out of stock", HttpStatus.CONFLICT),

    // 4xxx – Database Errors
    DATABASE_CONNECTION_FAILED(4001, "Failed to connect to the database", HttpStatus.INTERNAL_SERVER_ERROR),
    DUPLICATE_ENTRY(4002, "Duplicate entry detected", HttpStatus.CONFLICT),
    CONSTRAINT_VIOLATION(4003, "Database constraint violation", HttpStatus.BAD_REQUEST),
    DATA_INTEGRITY_ERROR(4004, "Data integrity error", HttpStatus.INTERNAL_SERVER_ERROR),
    SQL_SYNTAX_ERROR(4005, "SQL syntax error", HttpStatus.BAD_REQUEST),

    // 9xxx – System
    INTERNAL_ERROR(9999, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}