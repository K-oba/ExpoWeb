package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Click;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Click entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClickRepository extends JpaRepository<Click,Long> {

}
