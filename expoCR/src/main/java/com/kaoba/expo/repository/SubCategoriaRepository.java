package com.kaoba.expo.repository;

import com.kaoba.expo.domain.SubCategoria;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the SubCategoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubCategoriaRepository extends JpaRepository<SubCategoria,Long> {

    @Query("select s from SubCategoria s left join fetch s.categoria c left join fetch c.exposicions e where e.id=:expoId")
    List<SubCategoria> findByExpo(@Param("expoId") Long expoId);

}
