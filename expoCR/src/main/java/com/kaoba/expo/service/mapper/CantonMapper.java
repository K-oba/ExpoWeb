package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.CantonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Canton and its DTO CantonDTO.
 */
@Mapper(componentModel = "spring", uses = {ProvinciaMapper.class, })
public interface CantonMapper extends EntityMapper <CantonDTO, Canton> {

    @Mapping(source = "provincia.id", target = "provinciaId")
    CantonDTO toDto(Canton canton); 

    @Mapping(source = "provinciaId", target = "provincia")
    @Mapping(target = "distritos", ignore = true)
    Canton toEntity(CantonDTO cantonDTO); 
    default Canton fromId(Long id) {
        if (id == null) {
            return null;
        }
        Canton canton = new Canton();
        canton.setId(id);
        return canton;
    }
}
