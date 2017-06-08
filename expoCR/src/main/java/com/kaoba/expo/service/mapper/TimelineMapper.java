package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.TimelineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Timeline and its DTO TimelineDTO.
 */
@Mapper(componentModel = "spring", uses = {ExposicionMapper.class, })
public interface TimelineMapper extends EntityMapper <TimelineDTO, Timeline> {

    @Mapping(source = "exposicion.id", target = "exposicionId")
    TimelineDTO toDto(Timeline timeline); 

    @Mapping(source = "exposicionId", target = "exposicion")
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    Timeline toEntity(TimelineDTO timelineDTO); 
    default Timeline fromId(Long id) {
        if (id == null) {
            return null;
        }
        Timeline timeline = new Timeline();
        timeline.setId(id);
        return timeline;
    }
}
