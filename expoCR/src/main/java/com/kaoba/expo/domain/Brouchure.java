package com.kaoba.expo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Brouchure.
 */
@Entity
@Table(name = "brouchure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Brouchure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "urlimagen")
    private String urlimagen;

    @OneToOne(mappedBy = "brouchure")
    @JsonIgnore
    private Stand stand;

    @OneToOne(mappedBy = "brouchure")
    @JsonIgnore
    private SubCategoria subCategoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Brouchure nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Brouchure descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlimagen() {
        return urlimagen;
    }

    public Brouchure urlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
        return this;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
    }

    public Stand getStand() {
        return stand;
    }

    public Brouchure stand(Stand stand) {
        this.stand = stand;
        return this;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public Brouchure subCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
        return this;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Brouchure brouchure = (Brouchure) o;
        if (brouchure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), brouchure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Brouchure{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", urlimagen='" + getUrlimagen() + "'" +
            "}";
    }
}
