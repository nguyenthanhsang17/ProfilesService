package com.a2m.profileservice.controller;

import com.a2m.profileservice.dto.ApiResponse;
import com.a2m.profileservice.dto.BusinessProfileDTOs.BusinessProfilesDTO;
import com.a2m.profileservice.dto.Paging.PageResult;
import com.a2m.profileservice.dto.response.PageResponse;
import com.a2m.profileservice.dto.student_profilesDTOs.student_profilesDTO;
import com.a2m.profileservice.mapper.StudentProfilesMapper;
import com.a2m.profileservice.service.BusinessProfileService;
import com.a2m.profileservice.service.StudentProfileService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private final StudentProfileService studentProfileService;
    private final BusinessProfileService businessProfileService;

    @GetMapping("/student/list")
    public ResponseEntity<ApiResponse<PageResponse<student_profilesDTO>>> getAllProfiles(@RequestParam String search,
                                                                                         @RequestParam String cursor,
                                                                                         @RequestParam int limit) {
        var api = studentProfileService.GetStudentProfile(search, cursor, limit);
        return ResponseEntity.ok(api);
    }


    @GetMapping("/business/list")
    public ResponseEntity<ApiResponse<PageResponse<BusinessProfilesDTO>>> getAllBusiness(@RequestParam String search,
                                                                                         @RequestParam String cursor,
                                                                                         @RequestParam int isApproved,
                                                                                         @RequestParam int limit) {
        var api = businessProfileService.getAllBusinessProfiles(search,isApproved, cursor, limit);
        return ResponseEntity.ok(api);
    }


    @GetMapping("/student/detail")
    public ResponseEntity<ApiResponse<student_profilesDTO>> getStudentProfile(@RequestParam String id) {
        var api = studentProfileService.getProfileStudentById(id);
        return ResponseEntity.ok(api);
    }

    @GetMapping("/business/detail")
    public ResponseEntity<ApiResponse<BusinessProfilesDTO>> getBusinessProfile(@RequestParam String id) {
        var api = businessProfileService.getBusinessProfileById_2(id);
        ApiResponse<BusinessProfilesDTO> apiResponse = new ApiResponse<>();
        apiResponse.setData(api);
        apiResponse.setMessage("Success");
        apiResponse.setCode(200);
        return ResponseEntity.ok(apiResponse);
    }
}
