package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Beacon;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Beacon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeaconRepository extends JpaRepository<Beacon,Long> {

}
