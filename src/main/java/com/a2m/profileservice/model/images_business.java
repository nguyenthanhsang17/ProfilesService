package com.a2m.profileservice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class images_business {
    private String imageId;
    private String businessId;
    private String imageUrl;
    private boolean isDeleted;
}
