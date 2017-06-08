package com.kaoba.expo.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Distrito entity.
 */
public class DistritoDTO implements Serializable {

    private Long id;

    private String nombre;

    private Long cantonId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCantonId() {
        return cantonId;
    }

    public void setCantonId(Long cantonId) {
        this.cantonId = cantonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DistritoDTO distritoDTO = (DistritoDTO) o;
        if(distritoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), distritoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DistritoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
