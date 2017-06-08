package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Canton;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Canton entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CantonRepository extends JpaRepository<Canton,Long> {

}
