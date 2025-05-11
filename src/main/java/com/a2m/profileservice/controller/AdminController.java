package com.a2m.profileservice.controller;

import com.a2m.profileservice.dto.ApiResponse;
import com.a2m.profileservice.dto.Paging.PageResult;
import com.a2m.profileservice.dto.response.PageResponse;
import com.a2m.profileservice.dto.student_profilesDTOs.student_profilesDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {
    @GetMapping("/student/list")
    public ResponseEntity<ApiResponse<PageResponse<student_profilesDTO>>> getAllProfiles() {
        return  null;
    }
}
