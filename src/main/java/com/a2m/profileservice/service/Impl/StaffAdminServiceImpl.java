package com.a2m.profileservice.service.Impl;

import com.a2m.profileservice.dto.NotificationMessage;
import com.a2m.profileservice.dto.request.UpdateRequestStatus;
import com.a2m.profileservice.dto.response.*;
import com.a2m.profileservice.exception.AppException;
import com.a2m.profileservice.exception.EntityNotFoundException;
import com.a2m.profileservice.exception.ErrorCode;
import com.a2m.profileservice.mapper.*;
import com.a2m.profileservice.model.BusinessProfiles;
import com.a2m.profileservice.model.RequestBusinesses;
import com.a2m.profileservice.model.RequestStudents;
import com.a2m.profileservice.model.student_profiles;
import com.a2m.profileservice.service.StaffAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffAdminServiceImpl implements StaffAdminService {
    private final RequestBusinessesMapper requestBusinessesMapper;
    private final RequestStudentMapper requestStudentMapper;
    private final BusinessProfilesMapper businessProfilesMapper;
    private final StudentProfilesMapper studentProfilesMapper;
    private final RequestStatsMapper requestStatsMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;


    @Override
    public List<RequestBusinessResponse> getAllRequestBusinessesWithCompanyName() {
        List<RequestBusinesses> requests = requestBusinessesMapper.getAllRequestBusiness();
        return requests.stream()
                .map(request -> RequestBusinessResponse.builder()
                        .request(request)
                        .companyName(businessProfilesMapper.getCompanyNameById(request.getBusinessId()))
                        .build())
                .toList();
    }

    @Override
    public List<RequestBusinessResponse> getRequestBusinessesByStatus(String status) {
        validateStatus(status);

        List<RequestBusinesses> requestBusinessList = requestBusinessesMapper.getAllRequestBusinessByStatus(status);

        return requestBusinessList.stream()
                .map(request -> {
                    String companyName = businessProfilesMapper.getCompanyNameById(request.getBusinessId());
                    return RequestBusinessResponse.builder()
                            .request(request)
                            .companyName(companyName)
                            .build();
                })
                .toList();
    }



    @Override
    public List<RequestStudentReponse> getRequestStudentsByStatus(String status) {
        validateStatus(status);
        List<RequestStudents> requests = requestStudentMapper.getRequestStudentByStatus(status);

        return requests.stream()
                .map(request -> {
                    String studentName = studentProfilesMapper.getStudentName(request.getStudentId());
                    return RequestStudentReponse.builder()
                            .requestStudents(request)
                            .studentName(studentName)
                            .build();
                })
                .toList();
    }
    @Override
    public List<RequestStudentReponse> getAllRequestStudentsWithName() {
        List<RequestStudents> requests = requestStudentMapper.getAllRequestStudents();

        return requests.stream()
                .map(request -> {
                    String studentName = studentProfilesMapper.getStudentName(request.getStudentId());
                    return RequestStudentReponse.builder()
                            .requestStudents(request)
                            .studentName(studentName)
                            .build();
                })
                .toList();
    }


    @Override
    public RequestBusinessDetailResponse getRequestBusinessesById(String id) {
        RequestBusinesses requestBusinesses = requestBusinessesMapper.getRequestBusinessById(id);
        if(requestBusinesses == null || requestBusinesses.isDeleted()) {
            throw new EntityNotFoundException("Request business with ID " + id + " not found");

        }
        BusinessProfiles businessProfiles = businessProfilesMapper.getBusinessProfileById(requestBusinesses.getBusinessId());
        return RequestBusinessDetailResponse.builder()
                .request(requestBusinesses)
                .businessProfile(businessProfiles)
                .build();
    }
    @Override
    public RequestStudentDetailResponse getRequestStudentById(String id) {
        RequestStudents request = requestStudentMapper.getRequestStudentById(id);
        if(request == null || request.isDeleted()) {
            throw new EntityNotFoundException("Request business with ID " + id + " not found");

        }
        student_profiles studentProfile = studentProfilesMapper.getById(request.getStudentId());

        return RequestStudentDetailResponse.builder()
                .request(request)
                .studentProfiles(studentProfile)
                .build();
    }
    @Override
    public void updateRequestStudentStatus(UpdateRequestStatus request) {
        if (!request.getStatus().equalsIgnoreCase("approve") && !request.getStatus().equalsIgnoreCase("reject")) {
            throw new AppException(ErrorCode.VALIDATION_ERROR);
        }

        getRequestStudentById(request.getId());

        requestStudentMapper.updateRequestStudentStatus(request.getId(), request.getStatus());

        if (request.getStatus().equalsIgnoreCase("reject")) {
            requestStudentMapper.updateRejectReason(request.getId(), request.getReason());
        }
    }



    @Override
    public void updateRequestBusinessStatus(UpdateRequestStatus request) {
        String status = request.getStatus().toLowerCase();
        if (!status.equals("approve") && !status.equals("reject")) {
            throw new AppException(ErrorCode.VALIDATION_ERROR);
        }

        RequestBusinessDetailResponse detail = getRequestBusinessesById(request.getId());
        requestBusinessesMapper.updateRequestBusinessStatus(request.getId(), status);

        if (status.equals("reject")) {
            requestBusinessesMapper.updateRejectReason(request.getId(), request.getReason());
        }

        BusinessProfiles business = detail.getBusinessProfile();

        String title;
        String message;
        String type;
        String url;

        if (status.equals("approve")) {
            title = "Tài khoản doanh nghiệp của bạn đã được xác minh!";
            message = "Bạn đã có thể đăng bài tuyển dụng và quản lý công ty.";
            type = "ACCOUNT_APPROVED";
            url = "/business/dashboard";
        } else {
            title = "Tài khoản doanh nghiệp của bạn KHÔNG được xác minh.";
            message = request.getReason() != null ? request.getReason() : "Hồ sơ chưa đủ điều kiện xác minh.";
            type = "ACCOUNT_REJECTED";
            url = "/business/profile";
        }

        sendBusinessNotification(business.getProfileId(), title, message, type, url);
    }



    //cursor pagination

    @Override
    public PageResponse<RequestStudentReponse> getRequestStudentByCursor(String status, String cursor, int limit) {
        Timestamp cursorTime = (cursor != null && !cursor.isEmpty()) ? Timestamp.valueOf(cursor) : null;
        List<RequestStudents> raw = requestStudentMapper.getRequestStudentsByCursor(status, cursorTime, limit);
        List<RequestStudentReponse> items = raw.stream()
                .map(request -> RequestStudentReponse.builder()
                        .requestStudents(request)
                        .studentName(studentProfilesMapper.getStudentName(request.getStudentId()))
                        .build())
                .toList();
        String nextCursor = raw.isEmpty() ? null : raw.get(raw.size() - 1).getSendTime().toString();
        return PageResponse.<RequestStudentReponse>builder()
                .items(items)
                .nextCursor(nextCursor)
                .hasMore(raw.size() == limit)
                .build();
    }

    @Override
    public PageResponse<RequestBusinessResponse> getRequestBusinessesByCursor(String status, String cursor, int limit) {
            Timestamp cursorTime = (cursor != null && !cursor.isEmpty()) ? Timestamp.valueOf(cursor) : null;
            List<RequestBusinesses> raw = requestBusinessesMapper.getRequestBusinessByCursor(status, cursorTime, limit);
            List<RequestBusinessResponse> items = raw.stream()
                    .map(request -> RequestBusinessResponse.builder()
                            .request(request)
                            .companyName(businessProfilesMapper.getCompanyNameById(request.getBusinessId()))
                            .build())
                    .toList();
            String nextCursor = raw.isEmpty() ? null : raw.get(raw.size() -1).getSendTime().toString();
            return  PageResponse.<RequestBusinessResponse>builder()
                    .items(items)
                    .nextCursor(nextCursor)
                    .hasMore(raw.size() == limit)
                    .build();
    }

    //help
    private void validateStatus(String status) {
        if (!List.of("pending", "approve", "reject").contains(status.toLowerCase())) {
            throw new AppException(ErrorCode.VALIDATION_ERROR);
        }
    }

    @Override
    public int getTotalPendingRequest(){
            return requestStatsMapper.countTotalPendingRequests();
    }
    @Override
    public int getPendingStudentRequest(){
        return requestStatsMapper.countPendingStudentRequests();
    }
    @Override
    public int getPendingBusinessRequest(){
        return requestStatsMapper.countPendingBusinessRequests();
    }


    //helper
    private void sendBusinessNotification(String userId, String title, String content, String type, String link) {
        NotificationMessage msg = new NotificationMessage(
                userId,
                title,
                content,
                type,
                link
        );
        amqpTemplate.convertAndSend("notification.exchange", "notify.account." + type.toLowerCase(), msg);
    }

}
