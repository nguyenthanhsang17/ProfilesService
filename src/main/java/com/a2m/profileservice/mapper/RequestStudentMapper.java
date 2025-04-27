package com.a2m.profileservice.mapper;

import com.a2m.profileservice.dto.student_profilesDTOs.student_profilesDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RequestStudentMapper {
    public boolean checkRequestStudentExists(String student_id);
    public boolean checkRequestStudentAlreadyRegistered(String student_id);
    public int SendRequest(String student_id);
}
