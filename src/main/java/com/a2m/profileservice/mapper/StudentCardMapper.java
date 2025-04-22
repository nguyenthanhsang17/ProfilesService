package com.a2m.profileservice.mapper;

import com.a2m.profileservice.model.StudentCard;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentCardMapper {
    public StudentCard getStudentCardByStudentId(String id);
    public int CreateStudentCard(StudentCard studentCard);
}
