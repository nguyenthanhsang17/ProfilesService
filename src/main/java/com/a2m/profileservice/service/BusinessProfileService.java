package com.a2m.profileservice.service;

import com.a2m.profileservice.model.business_profiles;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BusinessProfileService {
    business_profiles businessVerifycation(business_profiles businessProfiles, String profileId);
    Optional<String> findByTaxCode(String taxCode);

    business_profiles getBusinessProfileById(@Param("profileId") String profileId);

    business_profiles updateBusinessProfile(business_profiles businessProfiles);
}
