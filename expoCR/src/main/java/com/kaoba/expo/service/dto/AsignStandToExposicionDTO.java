package com.kaoba.expo.service.dto;

/**
 * Created by Robert on 7/4/17.
 */
public class AsignStandToExposicionDTO {
    private Long idExposicion;
    private Long idStand;

    public Long getIdExposicion() {
        return idExposicion;
    }

    public void setIdExposicion(Long idExposicion) {
        this.idExposicion = idExposicion;
    }

    public Long getIdStand() {
        return idStand;
    }

    public void setIdStand(Long idStand) {
        this.idStand = idStand;
    }

    @Override
    public String toString() {
        return "ExposicionDTO{" +
            "idExpo=" + getIdExposicion() +
            ", idStand='" + getIdStand() + "'" +
            "}";
    }
}
