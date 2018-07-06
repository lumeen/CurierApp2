package com.social.entities;

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
@Table(name="curiers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Courier implements Serializable {

    private static final long serialVersionUID = -1319441825834536307L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String secondName;

    private String email;

    private String phoneNumber;


    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "curier")
    private Car car;
}
