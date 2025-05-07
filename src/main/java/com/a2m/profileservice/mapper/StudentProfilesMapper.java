package com.a2m.profileservice.mapper;

import com.a2m.profileservice.model.student_profiles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentProfilesMapper {
    public List<student_profiles> getAll();
    public student_profiles getById(String id);
    public int checkExits(String id);
    int createStudentProfile(student_profiles studentProfiles);
    public int updateStudentProfile(student_profiles studentProfiles);
    int UpdateAvatar(@Param("avatar") String avatar, @Param("id") String id);

    String getStudentName(@Param("profileId") String profileId);

    boolean checkIfExists(String id);



}