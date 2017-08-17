package com.kaoba.expo.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the HistorialUsuariosExpo entity.
 */
public class HistorialUsuariosExpoDTO implements Serializable {

    private Long id;

    private Integer idExpo;

    private String deviceId;

    private Integer standId;

    private Integer subcategoryId;

    private Instant fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdExpo() {
        return idExpo;
    }

    public void setIdExpo(Integer idExpo) {
        this.idExpo = idExpo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getStandId() {
        return standId;
    }

    public void setStandId(Integer standId) {
        this.standId = standId;
    }

    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HistorialUsuariosExpoDTO historialUsuariosExpoDTO = (HistorialUsuariosExpoDTO) o;
        if(historialUsuariosExpoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historialUsuariosExpoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HistorialUsuariosExpoDTO{" +
            "id=" + getId() +
            ", idExpo='" + getIdExpo() + "'" +
            ", deviceId='" + getDeviceId() + "'" +
            ", standId='" + getStandId() + "'" +
            ", subcategoryId='" + getSubcategoryId() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
