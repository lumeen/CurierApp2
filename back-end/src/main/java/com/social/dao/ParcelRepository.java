package com.social.dao;

import com.social.entities.Parcel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {

    List<Parcel> findByIdIn(List<Long> id);
    List<Parcel> findById(Long id);
    List<Parcel> findByStartPhoneAndEndPhoneAndStatus(String startPhone, String ednPhone, String status);
    List<Parcel> findByStartPhoneAndStatus(String startPhone, String status);
        List<Parcel> findByCarId(Long carId);
    List<Parcel> findByEndPhoneAndStatus(String endPhone, String status);
List<Parcel> findByCourierId(Long courierId);

}
