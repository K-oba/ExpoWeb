package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Charla;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Charla entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CharlaRepository extends JpaRepository<Charla,Long> {

    @Query("select distinct charla from Charla charla left join fetch charla.preguntas")
    List<Charla> findAllWithEagerRelationships();

    @Query("select charla from Charla charla left join fetch charla.preguntas where charla.id =:id")
    Charla findOneWithEagerRelationships(@Param("id") Long id);

}
