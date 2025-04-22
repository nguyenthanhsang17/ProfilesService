package com.a2m.profileservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCode {
    // 1xxx – Auth
    USER_EXISTED(1001, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1002, "User not found", HttpStatus.NOT_FOUND),
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

    // 5xxx – Internship Application Errors
    STUDENT_ALREADY_REGISTERED(5001, "Student is already registered", HttpStatus.BAD_REQUEST),
    STUDENT_NOT_FOUND(5002, "Student not found", HttpStatus.NOT_FOUND),
    INTERNSHIP_NOT_FOUND(5003, "Internship not found", HttpStatus.NOT_FOUND),
    JOB_ALREADY_APPLIED(5004, "You have already applied for this job", HttpStatus.CONFLICT),
    JOB_NOT_FOUND(5005, "Job not found", HttpStatus.NOT_FOUND),
    EMPLOYER_NOT_FOUND(5006, "Employer not found", HttpStatus.NOT_FOUND),
    INTERNSHIP_CREATION_FAILED(5007, "Failed to create internship", HttpStatus.INTERNAL_SERVER_ERROR),
    APPLICATION_DUPLICATE(5008, "Application already exists", HttpStatus.CONFLICT),
    ROLE_ASSIGNMENT_FAILED(5009, "Failed to assign role", HttpStatus.INTERNAL_SERVER_ERROR),

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
