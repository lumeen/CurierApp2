package com.social.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParcelRequest {

/*    private String city;
    private String postCode;
    private String streetName;
    private String buildNumber;*/
    private String status;
    private Double startLat;
    private String weight;
    private Double startLng;
    private String endPhone;
    private String startPhone;
    private String userName;
}
