package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.RolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rol and its DTO RolDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RolMapper extends EntityMapper <RolDTO, Rol> {
    
    @Mapping(target = "usuarios", ignore = true)
    Rol toEntity(RolDTO rolDTO); 
    default Rol fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rol rol = new Rol();
        rol.setId(id);
        return rol;
    }
}
