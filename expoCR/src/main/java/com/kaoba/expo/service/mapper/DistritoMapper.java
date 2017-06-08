package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.DistritoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Distrito and its DTO DistritoDTO.
 */
@Mapper(componentModel = "spring", uses = {CantonMapper.class, })
public interface DistritoMapper extends EntityMapper <DistritoDTO, Distrito> {

    @Mapping(source = "canton.id", target = "cantonId")
    DistritoDTO toDto(Distrito distrito); 

    @Mapping(source = "cantonId", target = "canton")
    @Mapping(target = "exposicions", ignore = true)
    Distrito toEntity(DistritoDTO distritoDTO); 
    default Distrito fromId(Long id) {
        if (id == null) {
            return null;
        }
        Distrito distrito = new Distrito();
        distrito.setId(id);
        return distrito;
    }
}
