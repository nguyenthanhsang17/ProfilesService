package com.a2m.profileservice.mapper;

import com.a2m.profileservice.model.student_profiles;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface student_profilesMapper {
    public List<student_profiles> getAll();
    public student_profiles getById(String id);
    public int checkExits(String id);
    int createStudentProfile(student_profiles studentProfiles);
}