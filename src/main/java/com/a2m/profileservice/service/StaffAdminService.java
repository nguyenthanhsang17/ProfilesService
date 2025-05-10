package com.a2m.profileservice.service;

import com.a2m.profileservice.dto.request.UpdateRequestStatus;
import com.a2m.profileservice.dto.response.*;

import java.util.List;

public interface StaffAdminService {

    List<RequestBusinessResponse> getAllRequestBusinessesWithCompanyName();

    List<RequestBusinessResponse> getRequestBusinessesByStatus(String status);


    List<RequestStudentReponse> getRequestStudentsByStatus(String status);

    List<RequestStudentReponse> getAllRequestStudentsWithName();

    RequestBusinessDetailResponse getRequestBusinessesById(String id);

    RequestStudentDetailResponse getRequestStudentById(String id);


    void updateRequestBusinessStatus(UpdateRequestStatus request);

    void updateRequestStudentStatus(UpdateRequestStatus request);

    //cursor pagination
    PageResponse<RequestStudentReponse> getRequestStudentByCursor(String status, String cursor, int limit);

    PageResponse<RequestBusinessResponse> getRequestBusinessesByCursor(String status, String cursor, int limit);

    int getTotalPendingRequest();

    int getPendingStudentRequest();

    int getPendingBusinessRequest();
}
