package com.social.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

@Builder
@Entity
@Table(name="parcels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Parcel implements Serializable {

    private static final long serialVersionUID = -1319441825834436307L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

/*    private String city;

    private String postCode;

    private String streetName;

    private String buildNumber;*/

    private String status;

    private Double startLat;

    private Double startLng;
    private Double endLat;

    private Double endLng;


    private String startPlaceId;
    private String endPlaceId;
    private Long weight;
    private String startPhone ;
    private String endPhone ;
    private Long courierId;
    @Setter
    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;
}
