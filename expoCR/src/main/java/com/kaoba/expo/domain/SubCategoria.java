package com.kaoba.expo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SubCategoria.
 */
@Entity
@Table(name = "sub_categoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubCategoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo")
    private String tipo;

    @ManyToOne
    private Categoria categoria;

    @OneToOne
    @JoinColumn(unique = true)
    private Brouchure brouchure;

    @OneToOne
    @JoinColumn(unique = true)
    private Click click;

    @OneToOne
    @JoinColumn(unique = true)
    private Beacon beacon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public SubCategoria nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public SubCategoria tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public SubCategoria categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Brouchure getBrouchure() {
        return brouchure;
    }

    public SubCategoria brouchure(Brouchure brouchure) {
        this.brouchure = brouchure;
        return this;
    }

    public void setBrouchure(Brouchure brouchure) {
        this.brouchure = brouchure;
    }

    public Click getClick() {
        return click;
    }

    public SubCategoria click(Click click) {
        this.click = click;
        return this;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public SubCategoria beacon(Beacon beacon) {
        this.beacon = beacon;
        return this;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubCategoria subCategoria = (SubCategoria) o;
        if (subCategoria.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subCategoria.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubCategoria{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
