package com.kaoba.expo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Click.
 */
@Entity
@Table(name = "click")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Click implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "beacon_id")
    private String beaconId;

    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "coordenadas")
    private String coordenadas;

    @Column(name = "fecha_hora")
    private String fechaHora;

    @OneToOne(mappedBy = "click")
    @JsonIgnore
    private Stand stand;

    @OneToOne(mappedBy = "click")
    @JsonIgnore
    private SubCategoria subCategoria;

    @OneToOne(mappedBy = "click")
    @JsonIgnore
    private Exposicion exposicion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public Click beaconId(String beaconId) {
        this.beaconId = beaconId;
        return this;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public Click clientId(Integer clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public Click coordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
        return this;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public Click fechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
        return this;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Stand getStand() {
        return stand;
    }

    public Click stand(Stand stand) {
        this.stand = stand;
        return this;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public Click subCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
        return this;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public Exposicion getExposicion() {
        return exposicion;
    }

    public Click exposicion(Exposicion exposicion) {
        this.exposicion = exposicion;
        return this;
    }

    public void setExposicion(Exposicion exposicion) {
        this.exposicion = exposicion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Click click = (Click) o;
        if (click.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), click.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Click{" +
            "id=" + getId() +
            ", beaconId='" + getBeaconId() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", coordenadas='" + getCoordenadas() + "'" +
            ", fechaHora='" + getFechaHora() + "'" +
            "}";
    }
}
