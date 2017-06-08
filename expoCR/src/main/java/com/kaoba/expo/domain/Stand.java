package com.kaoba.expo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Stand.
 */
@Entity
@Table(name = "stand")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo")
    private String tipo;

    @OneToOne(mappedBy = "stand")
    @JsonIgnore
    private Usuario usuario;

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

    public Stand nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public Stand tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Stand usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Brouchure getBrouchure() {
        return brouchure;
    }

    public Stand brouchure(Brouchure brouchure) {
        this.brouchure = brouchure;
        return this;
    }

    public void setBrouchure(Brouchure brouchure) {
        this.brouchure = brouchure;
    }

    public Click getClick() {
        return click;
    }

    public Stand click(Click click) {
        this.click = click;
        return this;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public Stand beacon(Beacon beacon) {
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
        Stand stand = (Stand) o;
        if (stand.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stand.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stand{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
