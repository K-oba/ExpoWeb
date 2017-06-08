package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Provincia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Provincia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia,Long> {

}
