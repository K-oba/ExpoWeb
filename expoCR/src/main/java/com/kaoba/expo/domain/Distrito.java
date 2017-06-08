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
 * A Distrito.
 */
@Entity
@Table(name = "distrito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Distrito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    private Canton canton;

    @OneToMany(mappedBy = "distrito")
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

    public Distrito nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Canton getCanton() {
        return canton;
    }

    public Distrito canton(Canton canton) {
        this.canton = canton;
        return this;
    }

    public void setCanton(Canton canton) {
        this.canton = canton;
    }

    public Set<Exposicion> getExposicions() {
        return exposicions;
    }

    public Distrito exposicions(Set<Exposicion> exposicions) {
        this.exposicions = exposicions;
        return this;
    }

    public Distrito addExposicion(Exposicion exposicion) {
        this.exposicions.add(exposicion);
        exposicion.setDistrito(this);
        return this;
    }

    public Distrito removeExposicion(Exposicion exposicion) {
        this.exposicions.remove(exposicion);
        exposicion.setDistrito(null);
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
        Distrito distrito = (Distrito) o;
        if (distrito.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), distrito.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Distrito{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
