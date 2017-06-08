package com.kaoba.expo.repository;

import com.kaoba.expo.domain.SubCategoria;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SubCategoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubCategoriaRepository extends JpaRepository<SubCategoria,Long> {

}
