package com.kaoba.expo.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Canton entity.
 */
public class CantonDTO implements Serializable {

    private Long id;

    private String nombre;

    private Long provinciaId;

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

    public Long getProvinciaId() {
        return provinciaId;
    }

    public void setProvinciaId(Long provinciaId) {
        this.provinciaId = provinciaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CantonDTO cantonDTO = (CantonDTO) o;
        if(cantonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cantonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CantonDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
