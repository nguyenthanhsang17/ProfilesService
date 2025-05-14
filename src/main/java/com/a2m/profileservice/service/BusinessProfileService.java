package com.a2m.profileservice.service;

import com.a2m.profileservice.dto.BusinessProfileDTOs.BusinessProfilesForUpdate;
import com.a2m.profileservice.model.BusinessProfiles;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.a2m.profileservice.dto.ApiResponse;

import java.util.Optional;

@Service
public interface BusinessProfileService {
    BusinessProfiles businessVerifycation(BusinessProfiles businessProfiles, String profileId);
    Optional<String> findByTaxCode(String taxCode);

    // lấy thông tin của bản thân business
    BusinessProfiles getBusinessProfileById(@Param("profileId") String profileId);

    // lấy thông tin của business theo id bất kì
    BusinessProfiles getBusinessProfileByIdAny(@Param("profileId") String profileId);

    BusinessProfiles updateBusinessProfile(BusinessProfiles businessProfiles, @Param("profileId") String businessId);
    public ApiResponse<Boolean> checkProfileExist(String profileId);

    ApiResponse<?> updateBusinessProfileAfterFix(BusinessProfilesForUpdate businessProfiles, @Param("profileId") String businessId);
}
