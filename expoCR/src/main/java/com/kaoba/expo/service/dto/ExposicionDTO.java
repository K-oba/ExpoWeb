package com.kaoba.expo.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Exposicion entity.
 */
public class ExposicionDTO implements Serializable {

    private Long id;

    private String nombre;

    private String descripcion;

    private Boolean estadoExpo;

    private String fechaInicio;

    private String fechaFin;

    private String coordenadas;

    private Long distritoId;

    private Long categoriaId;

    private Set<AmenidadesDTO> amenidades = new HashSet<>();

    private Long clickId;

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

    public Boolean isEstadoExpo() {
        return estadoExpo;
    }

    public void setEstadoExpo(Boolean estadoExpo) {
        this.estadoExpo = estadoExpo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Long getDistritoId() {
        return distritoId;
    }

    public void setDistritoId(Long distritoId) {
        this.distritoId = distritoId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Set<AmenidadesDTO> getAmenidades() {
        return amenidades;
    }

    public void setAmenidades(Set<AmenidadesDTO> amenidades) {
        this.amenidades = amenidades;
    }

    public Long getClickId() {
        return clickId;
    }

    public void setClickId(Long clickId) {
        this.clickId = clickId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExposicionDTO exposicionDTO = (ExposicionDTO) o;
        if(exposicionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exposicionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExposicionDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", estadoExpo='" + isEstadoExpo() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", coordenadas='" + getCoordenadas() + "'" +
            "}";
    }
}
