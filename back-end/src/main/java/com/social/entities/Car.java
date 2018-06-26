package com.social.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Entity
@Table(name="cars")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "curier")

public class Car {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id ;

    private String registrationNumber;

    private String brand;

    private String model;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "curier_id", nullable = false)
    private Courier curier;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "car")
    private List<Parcel> parcels;

    @OneToOne(fetch = FetchType.LAZY,
        cascade =  CascadeType.ALL,
        mappedBy = "car")
    private Coordinate coordinate;
}
