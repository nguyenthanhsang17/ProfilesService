package com.a2m.profileservice.controller;

import com.a2m.profileservice.Authentication.JwtUtil;
import com.a2m.profileservice.dto.ApiResponse;
import com.a2m.profileservice.model.images_business;
import com.a2m.profileservice.service.ImagesBusinessUploadService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/business/images")
public class ImagesBusinessController {

    private final ImagesBusinessUploadService imagesBusinessUploadService;
    private final JwtUtil jwtUtil;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImagesBusiness(@RequestPart("files")
            List<MultipartFile> files, HttpServletRequest request
    ) {
        String businessId = (String) jwtUtil.extractUserId(request.getHeader("Authorization").substring(7));

        List<images_business> res = imagesBusinessUploadService.addImagesBusiness(files, businessId);
        ApiResponse<List<images_business>> response = ApiResponse.<List<images_business>>builder()
                .code(1000)
                .message("Images uploaded successfully")
                .data(res)
                .build();

        return ResponseEntity.ok(response.getData());
    }

    @GetMapping("/avatar")
    public ResponseEntity<?> getFirstImageBusinessByBusinessId(
            HttpServletRequest request
    ) {
        String businessId = (String) jwtUtil.extractUserId(request.getHeader("Authorization").substring(7));

        String res = imagesBusinessUploadService.getFirstImageBusinessByBusinessId(businessId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(1000)
                .message("Get first image successfully")
                .data(res)
                .build();

        return ResponseEntity.ok(response.getData());
    }
}
