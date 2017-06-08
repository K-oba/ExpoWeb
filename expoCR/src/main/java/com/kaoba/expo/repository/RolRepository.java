package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Rol;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Rol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RolRepository extends JpaRepository<Rol,Long> {

}
