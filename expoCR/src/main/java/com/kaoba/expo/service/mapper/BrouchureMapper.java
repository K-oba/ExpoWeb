package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.BrouchureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Brouchure and its DTO BrouchureDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BrouchureMapper extends EntityMapper <BrouchureDTO, Brouchure> {
    
    @Mapping(target = "stand", ignore = true)
    @Mapping(target = "subCategoria", ignore = true)
    Brouchure toEntity(BrouchureDTO brouchureDTO); 
    default Brouchure fromId(Long id) {
        if (id == null) {
            return null;
        }
        Brouchure brouchure = new Brouchure();
        brouchure.setId(id);
        return brouchure;
    }
}
