package com.a2m.profileservice.mapper;

import com.a2m.profileservice.model.images_business;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface images_businessMapper {

    int insertImagesBusiness(images_business imagesBusiness);
    List<images_business> getImagesBusinessByBusinessId(@Param("businessId") String businessId);
    images_business getFirstImageBusinessByBusinessId(@Param("businessId") String businessId);

}
