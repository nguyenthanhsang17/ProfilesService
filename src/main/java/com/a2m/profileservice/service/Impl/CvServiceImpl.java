package com.a2m.profileservice.service.Impl;

import com.a2m.profileservice.dto.ApiResponse;
import com.a2m.profileservice.service.CVSerrvice;
import org.springframework.web.multipart.MultipartFile;

public class CvServiceImpl implements CVSerrvice {
    @Override
    public ApiResponse<Object> getCvByUserID(String id) {
        return null;
    }

    @Override
    public ApiResponse<Object> uploadCv(String id, MultipartFile file) {
        return null;
    }

    @Override
    public ApiResponse<Object> deleteCv(String uid, String cvid) {
        return null;
    }
}
