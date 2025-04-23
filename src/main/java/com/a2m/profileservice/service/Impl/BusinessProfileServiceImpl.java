package com.a2m.profileservice.service.Impl;

import com.a2m.profileservice.exception.AppException;
import com.a2m.profileservice.exception.ErrorCode;
import com.a2m.profileservice.mapper.business_profilesMapper;
import com.a2m.profileservice.model.business_profiles;
import com.a2m.profileservice.service.BusinessProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BusinessProfileServiceImpl implements BusinessProfileService {

    private business_profilesMapper businessProfilesMapper;

    @Override
    public business_profiles businessVerifycation(business_profiles businessProfiles, String profileId) {
        Optional<String> existingTaxCode = businessProfilesMapper.findByTaxCode(businessProfiles.getTaxCode());
        if(existingTaxCode.isPresent()) {
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
    public business_profiles getBusinessProfileById(String profileId){
        business_profiles existingBusinessProfile = businessProfilesMapper.getBusinessProfileById(profileId);
        if(existingBusinessProfile == null){
            throw new AppException(ErrorCode.BUSINESS_NOT_FOUND);
        }
        return existingBusinessProfile;
    }

    @Override
    public business_profiles updateBusinessProfile(business_profiles businessProfiles) {
        business_profiles existingBusinessProfile = businessProfilesMapper.getBusinessProfileById(businessProfiles.getProfileId());
        if(existingBusinessProfile == null){
            throw new AppException(ErrorCode.BUSINESS_NOT_FOUND);
        }

        if(existingBusinessProfile.isApproved() == false){
            throw new AppException(ErrorCode.BUSINESS_NOT_AUTHORIZED);
        } else {
            if(existingBusinessProfile.getStatus() == "active") { // nếu đã acp thì không cho sửa
                throw new AppException(ErrorCode.BUSINESS_NOT_AUTHORIZED);
            }else {
                if(businessProfiles.getUpdatedAt() == null) {
                    businessProfiles.setUpdatedAt(LocalDateTime.now());
                }

                businessProfilesMapper.updateBusinessProfile(businessProfiles);
            }
        }

        return businessProfiles;
    }
}
