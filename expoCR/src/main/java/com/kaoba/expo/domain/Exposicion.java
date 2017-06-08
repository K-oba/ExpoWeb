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
 * A Exposicion.
 */
@Entity
@Table(name = "exposicion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Exposicion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado_expo")
    private Boolean estadoExpo;

    @Column(name = "fecha_inicio")
    private String fechaInicio;

    @Column(name = "fecha_fin")
    private String fechaFin;

    @Column(name = "coordenadas")
    private String coordenadas;

    @ManyToOne
    private Distrito distrito;

    @ManyToOne
    private Categoria categoria;

    @OneToMany(mappedBy = "exposicion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Charla> charlas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "exposicion_amenidades",
               joinColumns = @JoinColumn(name="exposicions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="amenidades_id", referencedColumnName="id"))
    private Set<Amenidades> amenidades = new HashSet<>();

    @OneToMany(mappedBy = "exposicion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Beacon> beacons = new HashSet<>();

    @OneToMany(mappedBy = "exposicion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Timeline> timelines = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private Click click;

    @OneToMany(mappedBy = "exposicion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    public Exposicion nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Exposicion descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean isEstadoExpo() {
        return estadoExpo;
    }

    public Exposicion estadoExpo(Boolean estadoExpo) {
        this.estadoExpo = estadoExpo;
        return this;
    }

    public void setEstadoExpo(Boolean estadoExpo) {
        this.estadoExpo = estadoExpo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public Exposicion fechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public Exposicion fechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public Exposicion coordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
        return this;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public Exposicion distrito(Distrito distrito) {
        this.distrito = distrito;
        return this;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Exposicion categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Set<Charla> getCharlas() {
        return charlas;
    }

    public Exposicion charlas(Set<Charla> charlas) {
        this.charlas = charlas;
        return this;
    }

    public Exposicion addCharla(Charla charla) {
        this.charlas.add(charla);
        charla.setExposicion(this);
        return this;
    }

    public Exposicion removeCharla(Charla charla) {
        this.charlas.remove(charla);
        charla.setExposicion(null);
        return this;
    }

    public void setCharlas(Set<Charla> charlas) {
        this.charlas = charlas;
    }

    public Set<Amenidades> getAmenidades() {
        return amenidades;
    }

    public Exposicion amenidades(Set<Amenidades> amenidades) {
        this.amenidades = amenidades;
        return this;
    }

    public Exposicion addAmenidades(Amenidades amenidades) {
        this.amenidades.add(amenidades);
        amenidades.getExposicions().add(this);
        return this;
    }

    public Exposicion removeAmenidades(Amenidades amenidades) {
        this.amenidades.remove(amenidades);
        amenidades.getExposicions().remove(this);
        return this;
    }

    public void setAmenidades(Set<Amenidades> amenidades) {
        this.amenidades = amenidades;
    }

    public Set<Beacon> getBeacons() {
        return beacons;
    }

    public Exposicion beacons(Set<Beacon> beacons) {
        this.beacons = beacons;
        return this;
    }

    public Exposicion addBeacon(Beacon beacon) {
        this.beacons.add(beacon);
        beacon.setExposicion(this);
        return this;
    }

    public Exposicion removeBeacon(Beacon beacon) {
        this.beacons.remove(beacon);
        beacon.setExposicion(null);
        return this;
    }

    public void setBeacons(Set<Beacon> beacons) {
        this.beacons = beacons;
    }

    public Set<Timeline> getTimelines() {
        return timelines;
    }

    public Exposicion timelines(Set<Timeline> timelines) {
        this.timelines = timelines;
        return this;
    }

    public Exposicion addTimeline(Timeline timeline) {
        this.timelines.add(timeline);
        timeline.setExposicion(this);
        return this;
    }

    public Exposicion removeTimeline(Timeline timeline) {
        this.timelines.remove(timeline);
        timeline.setExposicion(null);
        return this;
    }

    public void setTimelines(Set<Timeline> timelines) {
        this.timelines = timelines;
    }

    public Click getClick() {
        return click;
    }

    public Exposicion click(Click click) {
        this.click = click;
        return this;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    public Set<Pregunta> getPreguntas() {
        return preguntas;
    }

    public Exposicion preguntas(Set<Pregunta> preguntas) {
        this.preguntas = preguntas;
        return this;
    }

    public Exposicion addPregunta(Pregunta pregunta) {
        this.preguntas.add(pregunta);
        pregunta.setExposicion(this);
        return this;
    }

    public Exposicion removePregunta(Pregunta pregunta) {
        this.preguntas.remove(pregunta);
        pregunta.setExposicion(null);
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
        Exposicion exposicion = (Exposicion) o;
        if (exposicion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exposicion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Exposicion{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", estadoExpo='" + isEstadoExpo() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", coordenadas='" + getCoordenadas() + "'" +
            "}";
    }
}
