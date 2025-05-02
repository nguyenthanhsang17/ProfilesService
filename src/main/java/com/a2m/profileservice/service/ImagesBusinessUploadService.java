package com.a2m.profileservice.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import com.a2m.profileservice.model.images_business;
public interface ImagesBusinessUploadService {
    List<images_business> addImagesBusiness(List<MultipartFile> images, String businessId);
    int deleteImagesBusiness(String imageId);

    List<images_business> getImagesBusinessByBusinessId(String businessId);

    String getFirstImageBusinessByBusinessId(String businessId);

}
