package com.a2m.profileservice.service.Impl;

import com.a2m.profileservice.dto.ApiResponse;
import com.a2m.profileservice.dto.BusinessProfileDTOs.BusinessProfilesForUpdate;
import com.a2m.profileservice.exception.AppException;
import com.a2m.profileservice.exception.ErrorCode;
import com.a2m.profileservice.mapper.BusinessProfilesMapper;
import com.a2m.profileservice.mapper.ImagesBusinessMapper;
import com.a2m.profileservice.model.BusinessProfiles;
import com.a2m.profileservice.model.ImagesBusiness;
import com.a2m.profileservice.service.BusinessProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BusinessProfileServiceImpl implements BusinessProfileService {

    private BusinessProfilesMapper businessProfilesMapper;
    private ImagesBusinessMapper imagesBusinessMapper;

    @Override
    public BusinessProfiles businessVerifycation(BusinessProfiles businessProfiles, String profileId) {
        Optional<String> existingTaxCode = businessProfilesMapper.findByTaxCode(businessProfiles.getTaxCode());
        BusinessProfiles existingBusiness = businessProfilesMapper.getBusinessProfileById(profileId);
        if(existingTaxCode.isPresent() || existingBusiness != null) {
            throw new AppException(ErrorCode.BUSINESS_EXISTED);
        }

        if(businessProfiles.getCreatedAt() == null) {
            businessProfiles.setCreatedAt(LocalDateTime.now());
        }

        businessProfiles.setProfileId(profileId);
        businessProfiles.setApproved(false);
//        businessProfiles.setStatus("inactive");
        businessProfiles.setDeleted(false);

        businessProfilesMapper.businessVerifycation(businessProfiles, profileId);
        return businessProfiles;
    }

    @Override
    public Optional<String> findByTaxCode(String taxCode) {
        String existingTaxCode = businessProfilesMapper.findByTaxCode(taxCode).orElse(null);
        if (existingTaxCode != null) {
            return Optional.of(existingTaxCode);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public BusinessProfiles getBusinessProfileById(String profileId){
        BusinessProfiles existingBusinessProfile = businessProfilesMapper.getBusinessProfileById(profileId);
        if(existingBusinessProfile == null){
            throw new AppException(ErrorCode.BUSINESS_NOT_FOUND);
        }
        return existingBusinessProfile;
    }

    @Override
    public BusinessProfiles updateBusinessProfile(BusinessProfiles businessProfiles, String businessId) {
        BusinessProfiles existingBusinessProfile = businessProfilesMapper.getBusinessProfileById(businessId);
        if(existingBusinessProfile == null){
            throw new AppException(ErrorCode.BUSINESS_NOT_FOUND);
        }

        if(!existingBusinessProfile.isApproved()){
            throw new AppException(ErrorCode.BUSINESS_NOT_AUTHORIZED);
        } else {
            if(Objects.equals(existingBusinessProfile.getStatus(), "active")) { // nếu đã acp thì không cho sửa
                throw new AppException(ErrorCode.BUSINESS_NOT_AUTHORIZED);
            }else {
                existingBusinessProfile.setCompanyName(businessProfiles.getCompanyName());
                existingBusinessProfile.setIndustry(businessProfiles.getIndustry());
                existingBusinessProfile.setCompanyInfo(businessProfiles.getCompanyInfo());
                existingBusinessProfile.setWebsiteUrl(businessProfiles.getWebsiteUrl());
                existingBusinessProfile.setEmail(businessProfiles.getEmail());
                existingBusinessProfile.setPhoneNumber(businessProfiles.getPhoneNumber());
                existingBusinessProfile.setAddress(businessProfiles.getAddress());
                existingBusinessProfile.setUpdatedAt(LocalDateTime.now());

            }
        }
        businessProfilesMapper.updateBusinessProfile(existingBusinessProfile);

        return existingBusinessProfile;
    }

    @Override
    public ApiResponse<Boolean> checkProfileExist(String profileId) {
        var existingBusinessProfile = businessProfilesMapper.checkProfileExist(profileId);
        if(existingBusinessProfile==false){
            throw new AppException(ErrorCode.BUSINESS_Not_PROFILE);
        }
        var api = new ApiResponse<Boolean>();
        api.setCode(200);
        api.setData(existingBusinessProfile);
        api.setMessage("profile exist");
        return api;
    }

    @Override
    public ApiResponse<?> updateBusinessProfileAfterFix(BusinessProfilesForUpdate businessProfiles, String businessId) {
        BusinessProfiles businessProfilesenity = BusinessProfiles.builder().profileId(businessId)
                .companyName(businessProfiles.getCompanyName())
                .industry(businessProfiles.getIndustry())
                .companyInfo(businessProfiles.getCompanyInfo())
                .websiteUrl(businessProfiles.getWebsiteUrl())
                .taxCode(businessProfiles.getTaxCode())
                .email(businessProfiles.getEmail())
                .phoneNumber(businessProfiles.getPhoneNumber())
                .address(businessProfiles.getAddress())
                .updatedAt(LocalDateTime.now())
                .build();
        var row = businessProfilesMapper.updateBusinessProfileAfterFix(businessProfilesenity);
        if(row<=0){
            throw new AppException(ErrorCode.BUSINESS_NOT_FOUND);
        }

        List<ImagesBusiness> imagesBusinesses = imagesBusinessMapper.getImagesBusinessByBusinessId(businessId);
        List<String> imagesBusinessesOld = imagesBusinesses.stream().map(ImagesBusiness::getImageId).collect(Collectors.toList());
        List<String> imgNew = businessProfiles.getImagesOldImg();
        if (imgNew == null) {
            imgNew = new ArrayList<>(); // hoặc return luôn nếu null là hợp lệ
        }
        List<String> diff = getDifference(imagesBusinessesOld, imgNew);


        System.out.println("cardid diff: "+diff.size());
        for (String s : diff) {
            int r = imagesBusinessMapper.deleteImagesBusinessById(s);
            if (r != 1) {
                throw new AppException(ErrorCode.DATABASE_CONNECTION_FAILED);
            }
        }

        ApiResponse<?> api = new ApiResponse<Boolean>();
        api.setCode(200);
        api.setMessage("profile update successful");
        return api;
    }

    private List<String> getDifference(List<String> oldid, List<String> newids) {
        // Tạo một bản sao của list1 để giữ nguyên list1 ban đầu
        List<String> difference = new ArrayList<>(oldid);
        // Loại bỏ tất cả các phần tử có trong list2 khỏi difference
        difference.removeAll(newids);
        return difference;
    }
}
