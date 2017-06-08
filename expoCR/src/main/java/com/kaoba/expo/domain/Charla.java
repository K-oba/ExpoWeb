package com.kaoba.expo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Charla.
 */
@Entity
@Table(name = "charla")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Charla implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private String fechaInicio;

    @Column(name = "fecha_fin")
    private String fechaFin;

    @ManyToOne
    private Exposicion exposicion;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "charla_pregunta",
               joinColumns = @JoinColumn(name="charlas_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="preguntas_id", referencedColumnName="id"))
    private Set<Pregunta> preguntas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Charla nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Charla descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public Charla fechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public Charla fechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Exposicion getExposicion() {
        return exposicion;
    }

    public Charla exposicion(Exposicion exposicion) {
        this.exposicion = exposicion;
        return this;
    }

    public void setExposicion(Exposicion exposicion) {
        this.exposicion = exposicion;
    }

    public Set<Pregunta> getPreguntas() {
        return preguntas;
    }

    public Charla preguntas(Set<Pregunta> preguntas) {
        this.preguntas = preguntas;
        return this;
    }

    public Charla addPregunta(Pregunta pregunta) {
        this.preguntas.add(pregunta);
        pregunta.getCharlas().add(this);
        return this;
    }

    public Charla removePregunta(Pregunta pregunta) {
        this.preguntas.remove(pregunta);
        pregunta.getCharlas().remove(this);
        return this;
    }

    public void setPreguntas(Set<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Charla charla = (Charla) o;
        if (charla.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), charla.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Charla{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            "}";
    }
}
