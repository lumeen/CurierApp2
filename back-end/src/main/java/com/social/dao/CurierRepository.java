package com.social.dao;

import com.social.entities.Curier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurierRepository extends JpaRepository<Curier, Long> {
}
