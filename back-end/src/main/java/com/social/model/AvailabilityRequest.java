package com.social.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityRequest {

    private String userName;
    private String hours;
    private String weight;
    private String lat;
    private String lng;
}
