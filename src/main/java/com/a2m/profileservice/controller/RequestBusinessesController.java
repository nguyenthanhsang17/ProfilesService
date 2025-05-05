package com.a2m.profileservice.controller;

import com.a2m.profileservice.Authentication.JwtUtil;
import com.a2m.profileservice.dto.ApiResponse;
import com.a2m.profileservice.model.RequestBusinesses;
import com.a2m.profileservice.service.RequestBusinessesService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/request-business")
public class RequestBusinessesController {

    private final RequestBusinessesService requestBusinessesService;
    private final JwtUtil jwtUtil;

    @PostMapping("/insert-request-business")
    public ResponseEntity<ApiResponse<RequestBusinesses>> insertRequestBusiness(
            HttpServletRequest request
    ){
        String token = request.getHeader("Authorization").substring(7);
        String role = jwtUtil.extractRoleFromToken(token);

        if (role == null) {
            return ResponseEntity.status(403).body(ApiResponse.<RequestBusinesses>builder()
                    .code(403)
                    .message("Role not found in token")
                    .build());
        }

        if (!"BUSINESS".equals(role)) {
            return ResponseEntity.status(403).body(ApiResponse.<RequestBusinesses>builder()
                    .code(403)
                    .message("You are not authorized to perform this action")
                    .build());
        }

        String businessId = jwtUtil.extractUserId(token);

        if(businessId == null) {
            return ResponseEntity.status(403).body(ApiResponse.<RequestBusinesses>builder()
                    .code(403)
                    .message("Profile ID not found in token")
                    .build());
        }

        RequestBusinesses newRequestBusiness = requestBusinessesService.insertRequestBusiness(businessId);
        ApiResponse<RequestBusinesses> response = ApiResponse.<RequestBusinesses>builder()
                .code(1000)
                .message("Request Business created successfully")
                .data(newRequestBusiness)
                .build();
        return ResponseEntity.ok(response);
    }
}
