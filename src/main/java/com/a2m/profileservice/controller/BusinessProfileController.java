package com.a2m.profileservice.controller;

import com.a2m.profileservice.Authentication.JwtUtil;
import com.a2m.profileservice.dto.ApiResponse;
import com.a2m.profileservice.model.BusinessProfiles;
import com.a2m.profileservice.service.BusinessProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/business")
@AllArgsConstructor
public class BusinessProfileController {

    private final BusinessProfileService businessProfileService;
    private final JwtUtil jwtUtil;
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<BusinessProfiles>> businessVerifycation(
            @RequestBody BusinessProfiles businessProfiles, HttpServletRequest request
    ){
//        String authHeader = request.getHeader("Authorization");
//        System.out.println("Auth header = " + authHeader);
        String token = request.getHeader("Authorization").substring(7);
        String role = jwtUtil.extractRoleFromToken(token);

        if (role == null) {
            return ResponseEntity.status(403).body(ApiResponse.<BusinessProfiles>builder()
                    .code(403)
                    .message("Role not found in token")
                    .build());
        }

        if (!"BUSINESS".equals(role)) {
            return ResponseEntity.status(403).body(ApiResponse.<BusinessProfiles>builder()
                    .code(403)
                    .message("You are not authorized to perform this action")
                    .build());
        }

        String profileId = jwtUtil.extractUserId(token);

        if(profileId == null) {
            return ResponseEntity.status(403).body(ApiResponse.<BusinessProfiles>builder()
                    .code(403)
                    .message("Profile ID not found in token")
                    .build());
        }

        System.out.println("Profile id = " + profileId);

        BusinessProfiles newBusiness = businessProfileService.businessVerifycation(businessProfiles, profileId);
        ApiResponse<BusinessProfiles> response = ApiResponse.<BusinessProfiles>builder()
                .code(1000)
                .message("Business profile created successfully")
                .data(newBusiness)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<BusinessProfiles>> getBussinessProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(ApiResponse.<BusinessProfiles>builder()
                    .code(401)
                    .message("Missing or invalid Authorization header")
                    .build());
        }

        String token = authHeader.substring(7);
        String role = jwtUtil.extractRoleFromToken(token);

        if (role == null) {
            return ResponseEntity.status(403).body(ApiResponse.<BusinessProfiles>builder()
                    .code(403)
                    .message("Role not found in token")
                    .build());
        }

        if (!"BUSINESS".equals(role)) {
            return ResponseEntity.status(403).body(ApiResponse.<BusinessProfiles>builder()
                    .code(403)
                    .message("You are not authorized to perform this action")
                    .build());
        }

        String profileId = jwtUtil.extractUserId(token);
        BusinessProfiles existingBusiness = businessProfileService.getBusinessProfileById(profileId);

        ApiResponse<BusinessProfiles> response = ApiResponse.<BusinessProfiles>builder()
                .code(1000)
                .data(existingBusiness)
                .message("Business profile found")
                .build();

        return ResponseEntity.ok(response);
    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponse<BusinessProfiles>> updateBusinessProfile(
            @RequestBody BusinessProfiles businessProfiles,
            HttpServletRequest request
    ){
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body(ApiResponse.<BusinessProfiles>builder()
                        .code(401)
                        .message("Missing or invalid Authorization header")
                        .build());
            }

            String businessId = jwtUtil.extractUserId(authHeader.substring(7));
            BusinessProfiles updatedBusiness = businessProfileService.updateBusinessProfile(businessProfiles, businessId);

            ApiResponse<BusinessProfiles> response = ApiResponse.<BusinessProfiles>builder()
                    .code(1000)
                    .message("Business profile updated successfully")
                    .data(updatedBusiness)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // để thấy lỗi 500 thật sự
            return ResponseEntity.status(500).body(ApiResponse.<BusinessProfiles>builder()
                    .code(500)
                    .message("Internal Server Error: " + e.getMessage())
                    .build());
        }
    }



}
