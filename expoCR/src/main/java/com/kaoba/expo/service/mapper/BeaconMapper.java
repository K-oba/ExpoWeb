package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.BeaconDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Beacon and its DTO BeaconDTO.
 */
@Mapper(componentModel = "spring", uses = {ExposicionMapper.class, })
public interface BeaconMapper extends EntityMapper <BeaconDTO, Beacon> {

    @Mapping(source = "exposicion.id", target = "exposicionId")
    BeaconDTO toDto(Beacon beacon); 

    @Mapping(source = "exposicionId", target = "exposicion")
    @Mapping(target = "stand", ignore = true)
    @Mapping(target = "subCategoria", ignore = true)
    Beacon toEntity(BeaconDTO beaconDTO); 
    default Beacon fromId(Long id) {
        if (id == null) {
            return null;
        }
        Beacon beacon = new Beacon();
        beacon.setId(id);
        return beacon;
    }
}
