package com.kaoba.expo.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Pregunta entity.
 */
public class PreguntaDTO implements Serializable {

    private Long id;

    private String pregunta;

    private Long usuarioId;

    private Long exposicionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getExposicionId() {
        return exposicionId;
    }

    public void setExposicionId(Long exposicionId) {
        this.exposicionId = exposicionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PreguntaDTO preguntaDTO = (PreguntaDTO) o;
        if(preguntaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), preguntaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PreguntaDTO{" +
            "id=" + getId() +
            ", pregunta='" + getPregunta() + "'" +
            "}";
    }
}
