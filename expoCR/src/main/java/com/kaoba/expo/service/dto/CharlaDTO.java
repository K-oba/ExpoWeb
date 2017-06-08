package com.kaoba.expo.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Charla entity.
 */
public class CharlaDTO implements Serializable {

    private Long id;

    private String nombre;

    private String descripcion;

    private String fechaInicio;

    private String fechaFin;

    private Long exposicionId;

    private Set<PreguntaDTO> preguntas = new HashSet<>();

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

    public Long getExposicionId() {
        return exposicionId;
    }

    public void setExposicionId(Long exposicionId) {
        this.exposicionId = exposicionId;
    }

    public Set<PreguntaDTO> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(Set<PreguntaDTO> preguntas) {
        this.preguntas = preguntas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CharlaDTO charlaDTO = (CharlaDTO) o;
        if(charlaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), charlaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CharlaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            "}";
    }
}
