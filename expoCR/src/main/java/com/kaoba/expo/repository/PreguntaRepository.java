package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Pregunta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pregunta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta,Long> {

}
