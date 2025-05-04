package com.a2m.profileservice.controller;

import com.a2m.profileservice.dto.ApiResponse;
import com.a2m.profileservice.dto.student_profilesDTOs.avatarUpdateDTO;
import com.a2m.profileservice.dto.student_profilesDTOs.student_profilesDTO;
import com.a2m.profileservice.dto.student_profilesDTOs.student_profilesDTOForCreate;
import com.a2m.profileservice.dto.student_profilesDTOs.student_profilesDTOForUpdate;
import com.a2m.profileservice.service.ImageKitUploadService;
import com.a2m.profileservice.service.StudentProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.PUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student_profiles")
public class StudentProfileController {


    private final StudentProfileService studentProfileService;
    private ImageKitUploadService imageKitUploadService;

    @Autowired
    public StudentProfileController(StudentProfileService studentProfileService, ImageKitUploadService imageKitUploadService) {
        this.studentProfileService = studentProfileService;
        this.imageKitUploadService = imageKitUploadService;
    }

    @GetMapping("/decode")
    public ResponseEntity<?> tryDecodeFromToken(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        String email = (String) request.getAttribute("email");
        Map<String, String> response = new HashMap<>();
        response.put("userId", userId);
        response.put("email", email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getall")
    public ResponseEntity<ApiResponse<List<student_profilesDTO>>> getAll() {
        var api = studentProfileService.getAll();
        return ResponseEntity.ok(api);
    }

    @GetMapping("/viewprofile")
    public ResponseEntity<ApiResponse<student_profilesDTO>> getStudentProfiles(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        var api = studentProfileService.getProfileStudentById(userId);
        return ResponseEntity.ok(api);
    }

    @GetMapping("/GetForUpdate/{id}")
    public ResponseEntity<ApiResponse<student_profilesDTO>> getStudentProfilesForUpdate(@PathVariable String id) {
        var api = studentProfileService.getProfileStudentById(id);
        return ResponseEntity.ok(api);
    }

    //done
    @PostMapping(value = "/uploadAvatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        System.out.println("Uploading avatar"+userId);
        try {
            String result = imageKitUploadService.uploadImage(file, userId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload thất bại: " + e.getMessage());
        }
    }

    //done
    @PostMapping(value = "/uploadStudentCard", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadMultipleImages(@RequestParam("files") List<MultipartFile> files, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        String response = imageKitUploadService.uploadImages(files, userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<student_profilesDTOForCreate>> CreateProfile(@RequestBody student_profilesDTOForCreate studentProfilesDTO, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        System.out.println("sang"+ userId);
        String email = (String) request.getAttribute("email");
        var api = studentProfileService.CreateProfileStudent(studentProfilesDTO, userId);
        return ResponseEntity.ok(api);
    }

    @PutMapping(value = "/updateurlavatar")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody avatarUpdateDTO url) {
        String userId = (String) request.getAttribute("userId");
        var api = studentProfileService.updateAvatar(url.getAvatarUrl(), userId);
        return ResponseEntity.ok(api);
    }

    @PutMapping("/update")
    public ResponseEntity<?> UpdateProfile(@RequestBody student_profilesDTOForUpdate studentProfilesDTO, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        var api = studentProfileService.UpdateProfileStudent(studentProfilesDTO, userId);
        return ResponseEntity.ok(api);
    }

    @GetMapping("/checkprofileexits")
    public ResponseEntity<ApiResponse<?>> checkProfileExits(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        var api = studentProfileService.checkIfExists(userId);
        return ResponseEntity.ok(api);
    }

    @GetMapping("/checkprofileApprove")
    public ResponseEntity<ApiResponse<?>> checkProfileApprove(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        var api = studentProfileService.checkIfExists(userId);
        return ResponseEntity.ok(api);
    }




}
