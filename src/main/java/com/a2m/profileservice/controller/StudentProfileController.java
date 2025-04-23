package com.a2m.profileservice.controller;

import com.a2m.profileservice.dto.ApiResponse;
//import com.a2m.profileservice.dto.student_profilesDTOs.student_profilesDTO;
import com.a2m.profileservice.service.StudentProfileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student_profiles")
public class StudentProfileController {


    private final StudentProfileService studentProfileService;
    @Autowired
    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    @GetMapping("/decode")
    public ResponseEntity<?> tryDecodeFromToken(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        String email = (String) request.getAttribute("email");
        Map<String, String> response = new HashMap<>();
        response.put("userId", userId);
        response.put("email", email);
        return  ResponseEntity.ok(response);
    }

//    @GetMapping("/getall")
//    public ResponseEntity<ApiResponse<List<student_profilesDTO>>> getAll() {
//        var api =  studentProfileService.getAll();
//        return ResponseEntity.ok(api);
//    }


}
