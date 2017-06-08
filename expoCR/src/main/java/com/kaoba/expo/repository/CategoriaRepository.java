package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Categoria;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Categoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

}
