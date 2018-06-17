package com.social.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="cars")
@Data
public class Car {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id ;

    private String registrationNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curier_id", nullable = false)
    private Curier curier;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "car")
    private Coordinate coordinate;
}
