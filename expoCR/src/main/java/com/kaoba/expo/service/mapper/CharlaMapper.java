package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.CharlaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Charla and its DTO CharlaDTO.
 */
@Mapper(componentModel = "spring", uses = {ExposicionMapper.class, PreguntaMapper.class, })
public interface CharlaMapper extends EntityMapper <CharlaDTO, Charla> {

    @Mapping(source = "exposicion.id", target = "exposicionId")
    CharlaDTO toDto(Charla charla); 

    @Mapping(source = "exposicionId", target = "exposicion")
    Charla toEntity(CharlaDTO charlaDTO); 
    default Charla fromId(Long id) {
        if (id == null) {
            return null;
        }
        Charla charla = new Charla();
        charla.setId(id);
        return charla;
    }
}
