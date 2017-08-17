package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Exposicion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
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

    List<Exposicion> findByFechaInicioAndEstadoExpo(@Param("dateExpo") String dateExpo, @Param("expoState") Boolean expoState);

    @Query("SELECT e FROM Exposicion e WHERE e.estadoExpo = :expoState AND UPPER(e.nombre) LIKE CONCAT('%',UPPER(:name),'%') AND STR_TO_DATE(e.fechaInicio,'%d-%m-%Y') BETWEEN STR_TO_DATE(:startDate,'%d-%m-%Y') AND STR_TO_DATE(:endDate,'%d-%m-%Y')")
    List<Exposicion> findByFilters(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("expoState") Boolean expoState,@Param("name") String name);

    @Query("SELECT e FROM Exposicion e WHERE e.estadoExpo = :expoState AND STR_TO_DATE(e.fechaInicio,'%d-%m-%Y') BETWEEN STR_TO_DATE(:startDate,'%d-%m-%Y') AND STR_TO_DATE(:endDate,'%d-%m-%Y')")
    List<Exposicion> findBetweenDates(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("expoState") Boolean expoState);

    @Query("SELECT e FROM Exposicion e WHERE e.estadoExpo = :expoState AND UPPER(e.nombre) LIKE CONCAT('%',UPPER(:name),'%')")
    List<Exposicion> findByName(@Param("name") String name, @Param("expoState") Boolean expoState);

    @Query("SELECT e FROM Exposicion e WHERE STR_TO_DATE(e.fechaInicio,'%d-%m-%Y') >= STR_TO_DATE(:date ,'%d-%m-%Y') AND STR_TO_DATE(:date2 ,'%d-%m-%Y') >= STR_TO_DATE(e.fechaFin,'%d-%m-%Y')")
    List<Exposicion> findAllByDay(@Param("date") String date, @Param("date2") String date2);

    @SuppressWarnings("SameParameterValue")
    Page<Exposicion> findByEstadoExpo(boolean isLive, Pageable pageable);
}
