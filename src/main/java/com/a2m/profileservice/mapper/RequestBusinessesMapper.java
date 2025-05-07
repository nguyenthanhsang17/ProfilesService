package com.a2m.profileservice.mapper;

import com.a2m.profileservice.model.RequestBusinesses;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.sql.Timestamp;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface RequestBusinessesMapper {

    int insertRequestBusiness(@Param("requestId") String requestId,
                              @Param("businessId") String businessId);

//    List<RequestBusinesses> getAllRequestBusiness();

    RequestBusinesses getRequestBusinessById(@Param("requestId") String requestId);


    List<RequestBusinesses> getAllRequestBusiness();
    List<RequestBusinesses> getAllRequestBusinessByStatus(String status);

    void updateRequestBusinessStatus(@Param("requestId") String requestId, @Param("status") String status);

    void updateRejectReason(@Param("requestId") String requestId, @Param("reason") String reason);
    List<RequestBusinesses> getRequestBusinessByCursor(@Param("status") String status,
                                                       @Param("cursor") Timestamp cursor,
                                                       @Param("limit") int limit);



    RequestBusinesses getRequestBusinessByBusinessId(@Param("businessId") String businessId);


}
