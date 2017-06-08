package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Amenidades;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Amenidades entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmenidadesRepository extends JpaRepository<Amenidades,Long> {

}
