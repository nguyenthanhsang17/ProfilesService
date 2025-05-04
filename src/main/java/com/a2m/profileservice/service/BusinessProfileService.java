package com.a2m.profileservice.service;

import com.a2m.profileservice.model.BusinessProfiles;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.a2m.profileservice.dto.ApiResponse;

import java.util.Optional;

@Service
public interface BusinessProfileService {
    BusinessProfiles businessVerifycation(BusinessProfiles businessProfiles, String profileId);
    Optional<String> findByTaxCode(String taxCode);

    BusinessProfiles getBusinessProfileById(@Param("profileId") String profileId);

    BusinessProfiles updateBusinessProfile(BusinessProfiles businessProfiles, @Param("profileId") String businessId);
    public ApiResponse<Boolean> checkProfileExist(String profileId);
}
