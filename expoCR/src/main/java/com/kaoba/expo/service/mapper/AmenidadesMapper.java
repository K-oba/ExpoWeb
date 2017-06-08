package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.AmenidadesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Amenidades and its DTO AmenidadesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AmenidadesMapper extends EntityMapper <AmenidadesDTO, Amenidades> {
    
    @Mapping(target = "exposicions", ignore = true)
    Amenidades toEntity(AmenidadesDTO amenidadesDTO); 
    default Amenidades fromId(Long id) {
        if (id == null) {
            return null;
        }
        Amenidades amenidades = new Amenidades();
        amenidades.setId(id);
        return amenidades;
    }
}
