package com.social.dao;

import com.social.entities.Courier;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourierRepository extends JpaRepository<Courier, Long> {

    @Query("select c from Courier c " +
        "where (:firstName='' or :firstName is null or c.firstName like concat('%', :firstName, '%')) " +
        "and (:secondName ='' or :secondName is null or c.secondName like concat('%', :secondName, '%')) ")
    List<Courier> findByNames(@Param("firstName") String firstName,
        @Param("secondName") String secondName);

    Courier findById(Long id);
}
