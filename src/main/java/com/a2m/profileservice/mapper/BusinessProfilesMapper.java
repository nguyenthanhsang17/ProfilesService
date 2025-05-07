package com.a2m.profileservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.a2m.profileservice.model.BusinessProfiles;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface BusinessProfilesMapper {

    void businessVerifycation(@Param("businessProfiles") BusinessProfiles businessProfiles,
                              @Param("profileId") String profileId);

    Optional<String> findByTaxCode(@Param("taxCode") String taxCode);

    BusinessProfiles getBusinessProfileById(@Param("profileId") String profileId);

    int updateBusinessProfile(BusinessProfiles businessProfiles);
    boolean checkProfileExist(@Param("profileId") String profileId);


    int updateBusinessProfileAfterFix(BusinessProfiles businessProfiles);
    String getCompanyNameById(@Param("profileId") String profileId);

}
