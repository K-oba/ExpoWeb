package com.kaoba.expo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Pregunta.
 */
@Entity
@Table(name = "pregunta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pregunta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pregunta")
    private String pregunta;

    @ManyToOne
    private Usuario usuario;

    @ManyToMany(mappedBy = "preguntas")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Charla> charlas = new HashSet<>();

    @ManyToOne
    private Exposicion exposicion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public Pregunta pregunta(String pregunta) {
        this.pregunta = pregunta;
        return this;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Pregunta usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Charla> getCharlas() {
        return charlas;
    }

    public Pregunta charlas(Set<Charla> charlas) {
        this.charlas = charlas;
        return this;
    }

    public Pregunta addCharla(Charla charla) {
        this.charlas.add(charla);
        charla.getPreguntas().add(this);
        return this;
    }

    public Pregunta removeCharla(Charla charla) {
        this.charlas.remove(charla);
        charla.getPreguntas().remove(this);
        return this;
    }

    public void setCharlas(Set<Charla> charlas) {
        this.charlas = charlas;
    }

    public Exposicion getExposicion() {
        return exposicion;
    }

    public Pregunta exposicion(Exposicion exposicion) {
        this.exposicion = exposicion;
        return this;
    }

    public void setExposicion(Exposicion exposicion) {
        this.exposicion = exposicion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pregunta pregunta = (Pregunta) o;
        if (pregunta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pregunta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pregunta{" +
            "id=" + getId() +
            ", pregunta='" + getPregunta() + "'" +
            "}";
    }
}
