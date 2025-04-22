package com.a2m.profileservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageKitUploadService {
    public String uploadImage(MultipartFile file);
    public String uploadImages(List<MultipartFile> files);
}
