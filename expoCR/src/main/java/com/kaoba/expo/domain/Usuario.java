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
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correo")
    private String correo;

    @Column(name = "clave")
    private String clave;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "usuario_timeline",
               joinColumns = @JoinColumn(name="usuarios_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="timelines_id", referencedColumnName="id"))
    private Set<Timeline> timelines = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pregunta> preguntas = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private Stand stand;

    @ManyToOne
    private Rol rol;

    @OneToMany(mappedBy = "usuario")
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

    public Usuario nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public Usuario correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public Usuario clave(String clave) {
        this.clave = clave;
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Set<Timeline> getTimelines() {
        return timelines;
    }

    public Usuario timelines(Set<Timeline> timelines) {
        this.timelines = timelines;
        return this;
    }

    public Usuario addTimeline(Timeline timeline) {
        this.timelines.add(timeline);
        timeline.getUsuarios().add(this);
        return this;
    }

    public Usuario removeTimeline(Timeline timeline) {
        this.timelines.remove(timeline);
        timeline.getUsuarios().remove(this);
        return this;
    }

    public void setTimelines(Set<Timeline> timelines) {
        this.timelines = timelines;
    }

    public Set<Pregunta> getPreguntas() {
        return preguntas;
    }

    public Usuario preguntas(Set<Pregunta> preguntas) {
        this.preguntas = preguntas;
        return this;
    }

    public Usuario addPregunta(Pregunta pregunta) {
        this.preguntas.add(pregunta);
        pregunta.setUsuario(this);
        return this;
    }

    public Usuario removePregunta(Pregunta pregunta) {
        this.preguntas.remove(pregunta);
        pregunta.setUsuario(null);
        return this;
    }

    public void setPreguntas(Set<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public Stand getStand() {
        return stand;
    }

    public Usuario stand(Stand stand) {
        this.stand = stand;
        return this;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public Rol getRol() {
        return rol;
    }

    public Usuario rol(Rol rol) {
        this.rol = rol;
        return this;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Set<Exposicion> getExposicions() {
        return exposicions;
    }

    public Usuario exposicions(Set<Exposicion> exposicions) {
        this.exposicions = exposicions;
        return this;
    }

    public Usuario addExposicion(Exposicion exposicion) {
        this.exposicions.add(exposicion);
        exposicion.setUsuario(this);
        return this;
    }

    public Usuario removeExposicion(Exposicion exposicion) {
        this.exposicions.remove(exposicion);
        exposicion.setUsuario(null);
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
        Usuario usuario = (Usuario) o;
        if (usuario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usuario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", clave='" + getClave() + "'" +
            "}";
    }
}
