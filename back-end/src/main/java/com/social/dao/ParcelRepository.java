package com.social.dao;

import com.social.entities.Parcel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {

    List<Parcel> findByIdIn(List<Long> id);
    List<Parcel> findByCarId(Long carId);

}
