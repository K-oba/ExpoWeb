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
 * A Canton.
 */
@Entity
@Table(name = "canton")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Canton implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    private Provincia provincia;

    @OneToMany(mappedBy = "canton")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Distrito> distritos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Canton nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public Canton provincia(Provincia provincia) {
        this.provincia = provincia;
        return this;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Set<Distrito> getDistritos() {
        return distritos;
    }

    public Canton distritos(Set<Distrito> distritos) {
        this.distritos = distritos;
        return this;
    }

    public Canton addDistrito(Distrito distrito) {
        this.distritos.add(distrito);
        distrito.setCanton(this);
        return this;
    }

    public Canton removeDistrito(Distrito distrito) {
        this.distritos.remove(distrito);
        distrito.setCanton(null);
        return this;
    }

    public void setDistritos(Set<Distrito> distritos) {
        this.distritos = distritos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Canton canton = (Canton) o;
        if (canton.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), canton.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Canton{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
