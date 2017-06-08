package com.kaoba.expo.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Click entity.
 */
public class ClickDTO implements Serializable {

    private Long id;

    private String beaconId;

    private Integer clientId;

    private String coordenadas;

    private String fechaHora;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClickDTO clickDTO = (ClickDTO) o;
        if(clickDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clickDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClickDTO{" +
            "id=" + getId() +
            ", beaconId='" + getBeaconId() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", coordenadas='" + getCoordenadas() + "'" +
            ", fechaHora='" + getFechaHora() + "'" +
            "}";
    }
}
