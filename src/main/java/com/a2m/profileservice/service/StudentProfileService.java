package com.a2m.profileservice.service;

import com.a2m.profileservice.dto.ApiResponse;
import com.a2m.profileservice.dto.student_profilesDTOs.student_profilesDTO;
import com.a2m.profileservice.dto.student_profilesDTOs.student_profilesDTOForCreate;

import java.util.List;

public interface StudentProfileService {
    public ApiResponse<List<student_profilesDTO>> getAll();

    public ApiResponse<student_profilesDTO> getProfileStudentById(String id);
    public ApiResponse<student_profilesDTO> GetProfileStudentByUpdate(String id);
    public ApiResponse<Object> UpdateProfileStudent(student_profilesDTO student_profilesDTO, String id);
    public ApiResponse<student_profilesDTOForCreate> CreateProfileStudent(student_profilesDTOForCreate student_profilesDTO, String id);

}
