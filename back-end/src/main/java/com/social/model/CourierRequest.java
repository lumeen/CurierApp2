package com.social.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierRequest {

    private String firstName;
    private String secondName;
    private String phoneNumber;

}
