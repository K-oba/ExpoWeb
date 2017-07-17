package com.kaoba.expo.service.dto;

import java.util.Objects;

/**
 * Created by Robert on 6/20/17.
 */
public class InvitationDTO {
    private String email;
    private Long exposicionId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

        CharlaDTO charlaDTO = (CharlaDTO) o;
        if(charlaDTO.getId() == null || getExposicionId() == null) {
            return false;
        }
        return Objects.equals(getExposicionId(), charlaDTO.getId());
    }

    public int hashCode() {
        return Objects.hashCode(getExposicionId());
    }
    @Override
    public String toString() {
        return "CharlaDTO{" +
            "ExpoaicionId=" + getExposicionId() +
            ", email='" + getEmail() + "'" +
            "}";
    }
}

