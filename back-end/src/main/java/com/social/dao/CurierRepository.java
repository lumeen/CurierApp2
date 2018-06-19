package com.social.dao;

import com.social.entities.Curier;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CurierRepository extends JpaRepository<Curier, Long> {

    @Query("select c from Curier c " +
        "where (:firstName='' or :firstName is null or c.firstName like concat('%', :firstName, '%')) " +
        "and (:secondName ='' or :secondName is null or c.secondName like concat('%', :secondName, '%')) ")
    List<Curier> findByNames(@Param("firstName") String firstName,
        @Param("secondName") String secondName);

    Curier findById(Long id);
}
