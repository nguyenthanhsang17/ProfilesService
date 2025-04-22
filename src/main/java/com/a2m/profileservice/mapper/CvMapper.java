package com.a2m.profileservice.mapper;

import com.a2m.profileservice.dto.ApiResponse;
import org.apache.ibatis.annotations.Mapper;
import com.a2m.profileservice.model.*;
import java.util.List;

@Mapper
public interface CvMapper {
    List<ApiResponse<cv>> GetCv(String uid);
}
