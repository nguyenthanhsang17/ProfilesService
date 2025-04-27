package com.a2m.profileservice.service.Impl;

import com.a2m.profileservice.dto.ApiResponse;
import com.a2m.profileservice.dto.student_profilesDTOs.student_profilesDTO;
import com.a2m.profileservice.exception.AppException;
import com.a2m.profileservice.exception.ErrorCode;
import com.a2m.profileservice.mapper.RequestStudentMapper;
import com.a2m.profileservice.service.RequestStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestStudentServiceImpl implements RequestStudentService {

    private final RequestStudentMapper requestStudentMapper;
    @Autowired
    public RequestStudentServiceImpl(RequestStudentMapper requestStudentMapper) {
        this.requestStudentMapper = requestStudentMapper;
    }

    @Override
    public ApiResponse<String> SendRequest(String profileId) {
        boolean check = requestStudentMapper.checkRequestStudentExists(profileId);
        if (check) {
            throw new AppException(ErrorCode.REQUEST_STUDENT_ALREADY_REGISTERED);
        }
        int row = requestStudentMapper.SendRequest(profileId);
        if (row == 0) {
            throw new AppException(ErrorCode.DATABASE_CONNECTION_FAILED);
        }
        ApiResponse<String> response = new ApiResponse<>();
        response.setData(profileId);
        response.setCode(200);
        response.setMessage("Register Success");
        return response;
    }
}
