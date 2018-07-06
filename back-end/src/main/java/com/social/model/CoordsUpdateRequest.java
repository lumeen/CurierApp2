package com.social.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordsUpdateRequest {

    private String userName;
    private String latitude;
    private String longitude;
}
