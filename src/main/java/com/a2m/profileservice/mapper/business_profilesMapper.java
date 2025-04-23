package com.a2m.profileservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.a2m.profileservice.model.business_profiles;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface business_profilesMapper {

    void businessVerifycation(@Param("businessProfiles") business_profiles businessProfiles,
                              @Param("profileId") String profileId);

    Optional<String> findByTaxCode(@Param("taxCode") String taxCode);

    business_profiles getBusinessProfileById(@Param("profileId") String profileId);

    business_profiles updateBusinessProfile(business_profiles businessProfiles);
}
