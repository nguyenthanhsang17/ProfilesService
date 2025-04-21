package com.a2m.profileservice.service;

import com.a2m.profileservice.dto.ApiResponse;
import com.a2m.profileservice.dto.student_profilesDTOs.student_profilesDTO;

import java.util.List;

public interface StudentProfileService {
    public ApiResponse<List<student_profilesDTO>> getAll();
}
