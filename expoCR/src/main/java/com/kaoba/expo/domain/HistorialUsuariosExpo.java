package com.kaoba.expo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A HistorialUsuariosExpo.
 */
@Entity
@Table(name = "historial_usuarios_expo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HistorialUsuariosExpo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_expo")
    private Integer idExpo;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "stand_id")
    private Integer standId;

    @Column(name = "subcategory_id")
    private Integer subcategoryId;

    @Column(name = "fecha")
    private Instant fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdExpo() {
        return idExpo;
    }

    public HistorialUsuariosExpo idExpo(Integer idExpo) {
        this.idExpo = idExpo;
        return this;
    }

    public void setIdExpo(Integer idExpo) {
        this.idExpo = idExpo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public HistorialUsuariosExpo deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getStandId() {
        return standId;
    }

    public HistorialUsuariosExpo standId(Integer standId) {
        this.standId = standId;
        return this;
    }

    public void setStandId(Integer standId) {
        this.standId = standId;
    }

    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public HistorialUsuariosExpo subcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
        return this;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Instant getFecha() {
        return fecha;
    }

    public HistorialUsuariosExpo fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HistorialUsuariosExpo historialUsuariosExpo = (HistorialUsuariosExpo) o;
        if (historialUsuariosExpo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historialUsuariosExpo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HistorialUsuariosExpo{" +
            "id=" + getId() +
            ", idExpo='" + getIdExpo() + "'" +
            ", deviceId='" + getDeviceId() + "'" +
            ", standId='" + getStandId() + "'" +
            ", subcategoryId='" + getSubcategoryId() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
