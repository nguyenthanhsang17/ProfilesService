package com.a2m.profileservice.model;

import lombok.Data;

import java.util.Date;
@Data
public class RequestStudents {
    private String requestId;
    private String studentId;
    private String reason;
    private Date sendTime;
    private String status; // "pending", "approve", "reject"
    private boolean isDeleted;
}
