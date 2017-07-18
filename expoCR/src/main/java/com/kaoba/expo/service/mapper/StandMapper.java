package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.StandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Stand and its DTO StandDTO.
 */
@Mapper(componentModel = "spring", uses = {BrouchureMapper.class, ClickMapper.class, BeaconMapper.class, })
public interface StandMapper extends EntityMapper <StandDTO, Stand> {

    @Mapping(source = "brouchure.id", target = "brouchureId")

    @Mapping(source = "click.id", target = "clickId")

    @Mapping(source = "beacon.id", target = "beaconId")
    StandDTO toDto(Stand stand);
    @Mapping(target = "usuario", ignore = true)

    @Mapping(source = "brouchureId", target = "brouchure")

    @Mapping(source = "tipo", target = "tipo")

    @Mapping(source = "clickId", target = "click")

    @Mapping(source = "beaconId", target = "beacon")
    @Mapping(target = "exposicions", ignore = true)
    Stand toEntity(StandDTO standDTO);
    default Stand fromId(Long id) {
        if (id == null) {
            return null;
        }
        Stand stand = new Stand();
        stand.setId(id);
        return stand;
    }
}
