package com.kaoba.expo.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Beacon entity.
 */
public class BeaconDTO implements Serializable {

    private Long id;

    private String uuid;

    private Long exposicionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getExposicionId() {
        return exposicionId;
    }

    public void setExposicionId(Long exposicionId) {
        this.exposicionId = exposicionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BeaconDTO beaconDTO = (BeaconDTO) o;
        if(beaconDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), beaconDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BeaconDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
