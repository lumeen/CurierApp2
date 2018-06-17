package com.social.entities;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name="curiers")
@Data
public class Curier {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id ;

    private String firstName ;

    private String secondName ;
    

    private String fullName;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "curier")
    private Car car;
}
