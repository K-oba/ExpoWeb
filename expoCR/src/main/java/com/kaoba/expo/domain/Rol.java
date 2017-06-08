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
 * A Rol.
 */
@Entity
@Table(name = "rol")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "rol")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Usuario> usuarios = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Rol nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public Rol usuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
        return this;
    }

    public Rol addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.setRol(this);
        return this;
    }

    public Rol removeUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
        usuario.setRol(null);
        return this;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rol rol = (Rol) o;
        if (rol.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rol.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rol{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
