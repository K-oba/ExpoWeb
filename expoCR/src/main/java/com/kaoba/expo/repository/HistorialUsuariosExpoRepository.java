package com.kaoba.expo.repository;

import com.kaoba.expo.domain.HistorialUsuariosExpo;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HistorialUsuariosExpo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistorialUsuariosExpoRepository extends JpaRepository<HistorialUsuariosExpo,Long> {

    HistorialUsuariosExpo findByDeviceId(@Param("device_Id") String deviceId);

    @Query("select count(*) from HistorialUsuariosExpo where id_expo =:idExpo")
    int getPersonsExpo(@Param("idExpo") int idExpo);

    @Query("select count(*) from HistorialUsuariosExpo where id_expo =:idExpo and stand_id = :idStand")
    int getPersonsStandsExpo(@Param("idExpo")int idExpo, @Param("idStand")int idStand);
}
