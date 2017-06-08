package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Stand;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Stand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StandRepository extends JpaRepository<Stand,Long> {

}
