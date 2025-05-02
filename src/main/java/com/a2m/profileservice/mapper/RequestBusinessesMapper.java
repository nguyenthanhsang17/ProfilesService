package com.a2m.profileservice.mapper;

import com.a2m.profileservice.model.RequestBusinesses;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RequestBusinessesMapper {

    int insertRequestBusiness(@Param("requestId") String requestId,
                              @Param("businessId") String businessId);
    List<RequestBusinesses> getAllRequestBusiness();
    RequestBusinesses getRequestBusinessById(@Param("requestId") String requestId);
}
