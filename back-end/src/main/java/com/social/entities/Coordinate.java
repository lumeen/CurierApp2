package com.social.entities;

import lombok.Data;
import javax.persistence.*;

@Table(name="coordinates")
@Data
@Entity
public class Coordinate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer pointId;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
}
