package com.a2m.profileservice.service;

import com.a2m.profileservice.dto.BusinessProfileDTOs.BusinessProfilesDTO;
import com.a2m.profileservice.dto.BusinessProfileDTOs.BusinessProfilesForUpdate;
import com.a2m.profileservice.dto.response.PageResponse;
import com.a2m.profileservice.model.BusinessProfiles;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.a2m.profileservice.dto.ApiResponse;

import java.util.List;
import java.util.Optional;

@Service
public interface BusinessProfileService {
    BusinessProfiles businessVerifycation(BusinessProfiles businessProfiles, String profileId);
    Optional<String> findByTaxCode(String taxCode);

    BusinessProfiles getBusinessProfileById(@Param("profileId") String profileId);

    BusinessProfiles updateBusinessProfile(BusinessProfiles businessProfiles, @Param("profileId") String businessId);
    public ApiResponse<Boolean> checkProfileExist(String profileId);

    ApiResponse<?> updateBusinessProfileAfterFix(BusinessProfilesForUpdate businessProfiles, @Param("profileId") String businessId);

    ApiResponse<PageResponse<BusinessProfilesDTO>> getAllBusinessProfiles(@Param("search") String search,
                                                             @Param("isApproved") int isApproved,
                                                             @Param("cursor") String cursor,
                                                             @Param("limit") int limit);

    BusinessProfilesDTO getBusinessProfileById_2(@Param("profileId") String profileId);
}

