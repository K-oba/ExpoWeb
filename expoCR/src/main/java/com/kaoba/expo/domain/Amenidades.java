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
 * A Amenidades.
 */
@Entity
@Table(name = "amenidades")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Amenidades implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "amenidades")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exposicion> exposicions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Amenidades nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public Amenidades tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Amenidades descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Exposicion> getExposicions() {
        return exposicions;
    }

    public Amenidades exposicions(Set<Exposicion> exposicions) {
        this.exposicions = exposicions;
        return this;
    }

    public Amenidades addExposicion(Exposicion exposicion) {
        this.exposicions.add(exposicion);
        exposicion.getAmenidades().add(this);
        return this;
    }

    public Amenidades removeExposicion(Exposicion exposicion) {
        this.exposicions.remove(exposicion);
        exposicion.getAmenidades().remove(this);
        return this;
    }

    public void setExposicions(Set<Exposicion> exposicions) {
        this.exposicions = exposicions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Amenidades amenidades = (Amenidades) o;
        if (amenidades.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amenidades.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Amenidades{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
