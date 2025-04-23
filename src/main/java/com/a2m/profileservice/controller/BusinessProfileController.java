package com.a2m.profileservice.controller;

import com.a2m.profileservice.Authentication.JwtUtil;
import com.a2m.profileservice.dto.ApiResponse;
import com.a2m.profileservice.model.business_profiles;
import com.a2m.profileservice.service.BusinessProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/business")
@AllArgsConstructor
public class BusinessProfileController {

    private final BusinessProfileService businessProfileService;
    private final JwtUtil jwtUtil;
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<business_profiles>> businessVerifycation(
            @RequestBody business_profiles businessProfiles, HttpServletRequest request
    ){
//        String authHeader = request.getHeader("Authorization");
//        System.out.println("Auth header = " + authHeader);
        String role = (String) jwtUtil.extractRoleFromToken(request.getHeader("Authorization").substring(7));
        if(!role.equals("business")) {
            return ResponseEntity.status(403).body(ApiResponse.<business_profiles>builder()
                    .code(403)
                    .message("You are not authorized to perform this action")
                    .build());
        }

        String profileId = (String) jwtUtil.extractUserId(request.getHeader("Authorization").substring(7));

        System.out.println("Profile id = " + profileId);

        business_profiles newBusiness = businessProfileService.businessVerifycation(businessProfiles, profileId);
        ApiResponse<business_profiles> response = ApiResponse.<business_profiles>builder()
                .code(1000)
                .message("Business profile created successfully")
                .data(newBusiness)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<business_profiles>> getBussinessProfile(
            HttpServletRequest request
    ){
        String role = (String) jwtUtil.extractRoleFromToken(request.getHeader("Authorization").substring(7));
        if(!role.equals("business")) {
            return ResponseEntity.status(403).body(ApiResponse.<business_profiles>builder()
                    .code(403)
                    .message("You are not authorized to perform this action")
                    .build());
        }

        String profileId = (String) jwtUtil.extractUserId(request.getHeader("Authorization").substring(7));

        business_profiles existingBusiness = businessProfileService.getBusinessProfileById(profileId);
        ApiResponse<business_profiles> response = ApiResponse.<business_profiles>builder()
                .code(1000)
                .data(existingBusiness)
                .message("Business profile found")
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<business_profiles>> updateBusinessProfile(
            @RequestBody business_profiles businessProfiles
    ){
        business_profiles updatedBusiness = businessProfileService.updateBusinessProfile(businessProfiles);
        ApiResponse<business_profiles> response = ApiResponse.<business_profiles>builder()
                .code(1000)
                .message("Business profile updated successfully")
                .data(updatedBusiness)
                .build();
        return ResponseEntity.ok(response);
    }


}
