package com.kaoba.expo.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SubCategoria entity.
 */
public class SubCategoriaDTO implements Serializable {

    private Long id;

    private String nombre;

    private String tipo;

    private Long categoriaId;

    private Long brouchureId;

    private Long clickId;

    private Long beaconId;

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

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getBrouchureId() {
        return brouchureId;
    }

    public void setBrouchureId(Long brouchureId) {
        this.brouchureId = brouchureId;
    }

    public Long getClickId() {
        return clickId;
    }

    public void setClickId(Long clickId) {
        this.clickId = clickId;
    }

    public Long getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(Long beaconId) {
        this.beaconId = beaconId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubCategoriaDTO subCategoriaDTO = (SubCategoriaDTO) o;
        if(subCategoriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subCategoriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubCategoriaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
