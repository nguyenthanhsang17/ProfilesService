package com.a2m.profileservice.service.Impl;

import com.a2m.profileservice.mapper.RequestBusinessesMapper;
import com.a2m.profileservice.model.RequestBusinesses;
import com.a2m.profileservice.service.RequestBusinessesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RequestBusinessesServiceImpl implements RequestBusinessesService {

    private final RequestBusinessesMapper requestBusinessMapper;

    @Override
    public RequestBusinesses insertRequestBusiness(String businessId) {
        String requestId = UUID.randomUUID().toString();
        requestBusinessMapper.insertRequestBusiness(requestId, businessId);

        return RequestBusinesses.builder()
                .requestId(requestId)
                .businessId(businessId)
                .reason(null)
                .status("pending")
                .isDeleted(false)
                .build();
    }

    @Override
    public List<RequestBusinesses> getAllRequestBusiness() {
        return requestBusinessMapper.getAllRequestBusiness();
    }

    @Override
    public RequestBusinesses getRequestBusinessById(String requestId) {
        return null;
    }
}
