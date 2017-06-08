package com.kaoba.expo.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Brouchure entity.
 */
public class BrouchureDTO implements Serializable {

    private Long id;

    private String nombre;

    private String descripcion;

    private String urlimagen;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlimagen() {
        return urlimagen;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BrouchureDTO brouchureDTO = (BrouchureDTO) o;
        if(brouchureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), brouchureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BrouchureDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", urlimagen='" + getUrlimagen() + "'" +
            "}";
    }
}
