package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Timeline;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Timeline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimelineRepository extends JpaRepository<Timeline,Long> {

}
