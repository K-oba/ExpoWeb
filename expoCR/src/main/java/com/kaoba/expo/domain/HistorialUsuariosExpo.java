package com.kaoba.expo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
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
            "}";
    }
}
