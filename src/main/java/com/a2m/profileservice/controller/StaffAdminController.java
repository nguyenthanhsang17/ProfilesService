package com.a2m.profileservice.controller;

import com.a2m.profileservice.dto.ApiResponse;
import com.a2m.profileservice.dto.request.UpdateRequestStatus;
import com.a2m.profileservice.dto.response.*;
import com.a2m.profileservice.model.RequestBusinesses;
import com.a2m.profileservice.model.RequestStudents;
import com.a2m.profileservice.service.StaffAdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff-admin")
@AllArgsConstructor
public class StaffAdminController {
    private final StaffAdminService staffAdminService;

    @GetMapping("/request/students")
    public ResponseEntity<ApiResponse<List<RequestStudentReponse>>> getStudentRequests(
            @RequestParam(required = false) String status) {

        List<RequestStudentReponse> result = (status == null)
                ? staffAdminService.getAllRequestStudentsWithName()
                : staffAdminService.getRequestStudentsByStatus(status);

        return ResponseEntity.ok(
                ApiResponse.<List<RequestStudentReponse>>builder()
                        .message("Get Request Students successfully")
                        .data(result)
                        .build()
        );
    }


    @GetMapping("/request/business")
    public ResponseEntity<ApiResponse<List<RequestBusinessResponse>>> getBusinessRequests(
            @RequestParam(required = false) String status) {

        List<RequestBusinessResponse> result = (status == null)
                ? staffAdminService.getAllRequestBusinessesWithCompanyName()
                : staffAdminService.getRequestBusinessesByStatus(status);

        return ResponseEntity.ok(
                ApiResponse.<List<RequestBusinessResponse>>builder()
                        .message("Get request businesses with company name successfully")
                        .data(result)
                        .build()
        );
    }


    @GetMapping("/request/business/{id}")
    public ResponseEntity<ApiResponse<RequestBusinessDetailResponse>> getBusinessRequestById(@PathVariable String id) {
        RequestBusinessDetailResponse result = staffAdminService.getRequestBusinessesById(id);
        return ResponseEntity.ok(
                ApiResponse.<RequestBusinessDetailResponse>builder()
                        .message("Get request business detail successfully")
                        .data(result)
                        .build()
        );
    }
    @GetMapping("/request/students/{id}")
    public ResponseEntity<ApiResponse<RequestStudentDetailResponse>> getStudentRequestById(@PathVariable String id) {
        RequestStudentDetailResponse result = staffAdminService.getRequestStudentById(id);
        return ResponseEntity.ok(
                ApiResponse.<RequestStudentDetailResponse>builder()
                        .message("Get request student detail successfully")
                        .data(result)
                        .build()
        );
    }

    @PutMapping("/request/business/status")
    public ResponseEntity<ApiResponse<Void>> updateBusinessRequestStatus(
            @RequestBody UpdateRequestStatus request) {

        staffAdminService.updateRequestBusinessStatus(request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .message("Update status for business successfully")
                .build());
    }

    @PutMapping("/request/students/status")
    public ResponseEntity<ApiResponse<Void>> updateStudentRequestStatus(
            @RequestBody UpdateRequestStatus request) {

        staffAdminService.updateRequestStudentStatus(request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .message("Update status for student  successfully")
                .build());
    }

    //cursor pagination


//1. Lấy trang đầu tiên (danh sách mới nhất)
//    GET /api/v1/staff-admin/request/students/paging?status=approve&limit=10
// Không truyền cursor → BE sẽ lấy từ bản ghi mới nhất theo send_time DESC
//
//2. Lấy trang kế tiếp
//    GET /api/v1/staff-admin/request/students/paging?status=approve&cursor=2025-05-04+10:15:51&limit=10
// Truyền cursor là send_time của bản ghi cuối cùng trang trước → BE lấy các bản ghi cũ hơn
//
// 3. Lấy tất cả trạng thái (bỏ lọc status)
//    GET /api/v1/staff-admin/request/students/paging?limit=10
//  Không truyền status → lấy tất cả request students bất kể trạng thái


    @GetMapping("/request/students/paging")
    public ResponseEntity<ApiResponse<PageResponse<RequestBusinessResponse>>> getBusinessesRequestsByCursor(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int limit) {

        PageResponse<RequestBusinessResponse> result = staffAdminService.getRequestBusinessesByCursor(status, cursor, limit);

        return ResponseEntity.ok(
                ApiResponse.<PageResponse<RequestBusinessResponse>>builder()
                        .message("Get business requests with cursor pagination successfully")
                        .data(result)
                        .build()
        );
    }
}
