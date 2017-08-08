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

}
