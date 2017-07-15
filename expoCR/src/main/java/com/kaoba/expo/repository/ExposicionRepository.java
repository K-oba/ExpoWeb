package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Exposicion;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Exposicion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExposicionRepository extends JpaRepository<Exposicion,Long> {

    @Query("select distinct exposicion from Exposicion exposicion left join fetch exposicion.amenidades")
    List<Exposicion> findAllWithEagerRelationships();

    @Query("select exposicion from Exposicion exposicion left join fetch exposicion.amenidades where exposicion.id =:id")
    Exposicion findOneWithEagerRelationships(@Param("id") Long id);

    List<Exposicion> findByUsuarioId(@Param("userId") Long id);

}
