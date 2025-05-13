package com.a2m.profileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {
    private String userId;
    private String title;
    private String message;
    private String type;
    private String redirectUrl;
}