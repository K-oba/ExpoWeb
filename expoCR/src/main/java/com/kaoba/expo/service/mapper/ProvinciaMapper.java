package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.ProvinciaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Provincia and its DTO ProvinciaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProvinciaMapper extends EntityMapper <ProvinciaDTO, Provincia> {
    
    @Mapping(target = "cantons", ignore = true)
    Provincia toEntity(ProvinciaDTO provinciaDTO); 
    default Provincia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Provincia provincia = new Provincia();
        provincia.setId(id);
        return provincia;
    }
}
