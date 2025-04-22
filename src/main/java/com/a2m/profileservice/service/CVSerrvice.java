package com.a2m.profileservice.service;

import com.a2m.profileservice.dto.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CVSerrvice {
    public ApiResponse<Object> getCvByUserID(String id);
    public ApiResponse<Object> uploadCv(String id, MultipartFile file);
    public ApiResponse<Object> deleteCv(String uid, String cvid);
}
