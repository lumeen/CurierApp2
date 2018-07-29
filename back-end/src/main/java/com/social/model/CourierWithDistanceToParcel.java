package com.social.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierWithDistanceToParcel {

   private Long courierId;
   private Double distance;


}
