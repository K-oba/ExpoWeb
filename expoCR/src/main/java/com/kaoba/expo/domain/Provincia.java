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
 * A Provincia.
 */
@Entity
@Table(name = "provincia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Provincia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "provincia")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Canton> cantons = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Provincia nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Canton> getCantons() {
        return cantons;
    }

    public Provincia cantons(Set<Canton> cantons) {
        this.cantons = cantons;
        return this;
    }

    public Provincia addCanton(Canton canton) {
        this.cantons.add(canton);
        canton.setProvincia(this);
        return this;
    }

    public Provincia removeCanton(Canton canton) {
        this.cantons.remove(canton);
        canton.setProvincia(null);
        return this;
    }

    public void setCantons(Set<Canton> cantons) {
        this.cantons = cantons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Provincia provincia = (Provincia) o;
        if (provincia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), provincia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Provincia{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
