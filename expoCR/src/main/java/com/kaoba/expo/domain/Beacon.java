package com.kaoba.expo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Beacon.
 */
@Entity
@Table(name = "beacon")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Beacon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @ManyToOne
    private Exposicion exposicion;

    @OneToOne(mappedBy = "beacon")
    @JsonIgnore
    private Stand stand;

    @OneToOne(mappedBy = "beacon")
    @JsonIgnore
    private SubCategoria subCategoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public Beacon uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Exposicion getExposicion() {
        return exposicion;
    }

    public Beacon exposicion(Exposicion exposicion) {
        this.exposicion = exposicion;
        return this;
    }

    public void setExposicion(Exposicion exposicion) {
        this.exposicion = exposicion;
    }

    public Stand getStand() {
        return stand;
    }

    public Beacon stand(Stand stand) {
        this.stand = stand;
        return this;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public Beacon subCategoria(SubCategoria subCategoria) {
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
        Beacon beacon = (Beacon) o;
        if (beacon.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), beacon.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Beacon{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
