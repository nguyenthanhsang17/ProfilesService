package com.a2m.profileservice.model;

import lombok.*;

import javax.xml.crypto.Data;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestBusiness {
    private String requestId;
    private String businessId;
    private String reason;
    private Data sendTime;
    private String status; // "pending", "approve", "reject"
    private boolean isDeleted;
}
