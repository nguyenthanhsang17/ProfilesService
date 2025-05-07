package com.a2m.profileservice.dto.response;


import com.a2m.profileservice.model.BusinessProfiles;
import com.a2m.profileservice.model.RequestBusinesses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestBusinessResponse {
    private RequestBusinesses request;
    private String companyName;
}
