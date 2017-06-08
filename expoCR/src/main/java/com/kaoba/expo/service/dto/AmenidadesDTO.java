package com.kaoba.expo.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Amenidades entity.
 */
public class AmenidadesDTO implements Serializable {

    private Long id;

    private String nombre;

    private String tipo;

    private String descripcion;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AmenidadesDTO amenidadesDTO = (AmenidadesDTO) o;
        if(amenidadesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amenidadesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AmenidadesDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
