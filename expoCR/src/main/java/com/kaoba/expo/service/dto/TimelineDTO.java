package com.kaoba.expo.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Timeline entity.
 */
public class TimelineDTO implements Serializable {

    private Long id;

    private Long exposicionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        TimelineDTO timelineDTO = (TimelineDTO) o;
        if(timelineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timelineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimelineDTO{" +
            "id=" + getId() +
            "}";
    }
}
