package com.social.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParcelVerifyRequest {

    private String startPhone;
    private String endPhone;
    private Double endLat;
    private Double endLng;

}
