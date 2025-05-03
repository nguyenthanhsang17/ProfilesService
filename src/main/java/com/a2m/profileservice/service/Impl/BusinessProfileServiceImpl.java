package com.a2m.profileservice.service.Impl;

import com.a2m.profileservice.exception.AppException;
import com.a2m.profileservice.exception.ErrorCode;
import com.a2m.profileservice.mapper.BusinessProfilesMapper;
import com.a2m.profileservice.model.BusinessProfiles;
import com.a2m.profileservice.service.BusinessProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BusinessProfileServiceImpl implements BusinessProfileService {

    private BusinessProfilesMapper businessProfilesMapper;

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
}
