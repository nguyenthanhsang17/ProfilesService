package com.a2m.profileservice.mapper;

import com.a2m.profileservice.model.student_profiles;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface student_profilesMapper {
    public List<student_profiles> getAll();
}