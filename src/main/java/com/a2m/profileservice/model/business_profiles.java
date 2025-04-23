package com.a2m.profileservice.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class business_profiles {
    private String profileId;
    private String companyName;
    private String industry;
    private String companyInfo;
    private String websiteUrl;
    private String taxCode;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean isApproved;
    private String status;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
