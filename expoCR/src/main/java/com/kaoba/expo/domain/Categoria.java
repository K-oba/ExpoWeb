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
 * A Categoria.
 */
@Entity
@Table(name = "categoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exposicion> exposicions = new HashSet<>();

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SubCategoria> subCategorias = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Categoria nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Categoria descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Exposicion> getExposicions() {
        return exposicions;
    }

    public Categoria exposicions(Set<Exposicion> exposicions) {
        this.exposicions = exposicions;
        return this;
    }

    public Categoria addExposicion(Exposicion exposicion) {
        this.exposicions.add(exposicion);
        exposicion.setCategoria(this);
        return this;
    }

    public Categoria removeExposicion(Exposicion exposicion) {
        this.exposicions.remove(exposicion);
        exposicion.setCategoria(null);
        return this;
    }

    public void setExposicions(Set<Exposicion> exposicions) {
        this.exposicions = exposicions;
    }

    public Set<SubCategoria> getSubCategorias() {
        return subCategorias;
    }

    public Categoria subCategorias(Set<SubCategoria> subCategorias) {
        this.subCategorias = subCategorias;
        return this;
    }

    public Categoria addSubCategoria(SubCategoria subCategoria) {
        this.subCategorias.add(subCategoria);
        subCategoria.setCategoria(this);
        return this;
    }

    public Categoria removeSubCategoria(SubCategoria subCategoria) {
        this.subCategorias.remove(subCategoria);
        subCategoria.setCategoria(null);
        return this;
    }

    public void setSubCategorias(Set<SubCategoria> subCategorias) {
        this.subCategorias = subCategorias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Categoria categoria = (Categoria) o;
        if (categoria.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoria.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Categoria{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
