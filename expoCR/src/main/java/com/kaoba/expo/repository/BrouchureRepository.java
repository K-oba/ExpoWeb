package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Brouchure;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Brouchure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrouchureRepository extends JpaRepository<Brouchure,Long> {

}
