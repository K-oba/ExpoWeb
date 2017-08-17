package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.HistorialUsuariosExpoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HistorialUsuariosExpo and its DTO HistorialUsuariosExpoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HistorialUsuariosExpoMapper extends EntityMapper <HistorialUsuariosExpoDTO, HistorialUsuariosExpo> {
    
    
    default HistorialUsuariosExpo fromId(Long id) {
        if (id == null) {
            return null;
        }
        HistorialUsuariosExpo historialUsuariosExpo = new HistorialUsuariosExpo();
        historialUsuariosExpo.setId(id);
        return historialUsuariosExpo;
    }
}
