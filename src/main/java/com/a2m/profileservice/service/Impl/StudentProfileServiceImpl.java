package com.a2m.profileservice.service.Impl;

import com.a2m.profileservice.dto.ApiResponse;
//import com.a2m.profileservice.dto.student_profilesDTOs.student_profilesDTO;
import com.a2m.profileservice.mapper.student_profilesMapper;
import com.a2m.profileservice.model.student_profiles;
import com.a2m.profileservice.service.StudentProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentProfileServiceImpl implements StudentProfileService {



//    private final student_profilesMapper mapper;
//    @Autowired
//    public StudentProfileServiceImpl(student_profilesMapper mapper ) {
//        this.mapper = mapper;
//    }
//    @Override
//    public ApiResponse<List<student_profilesDTO>> getAll() {
//        List<student_profiles> student_profiles = mapper.getAll();
//        List<student_profilesDTO> student_profilesDTOs = new ArrayList<student_profilesDTO>();
//        student_profilesDTOs = student_profiles.stream()
//                .map(st -> new student_profilesDTO(st.getProfileId(), st.getFullName()))
//                .collect(Collectors.toList());
//        ApiResponse<List<student_profilesDTO>> apiResponse = new ApiResponse<>();
//        apiResponse.setData(student_profilesDTOs);
//        apiResponse.setMessage("Success");
//        apiResponse.setCode(200);
//        return apiResponse;
//    }
}
