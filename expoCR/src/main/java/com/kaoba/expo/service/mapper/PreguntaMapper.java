package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.PreguntaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pregunta and its DTO PreguntaDTO.
 */
@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, ExposicionMapper.class, })
public interface PreguntaMapper extends EntityMapper <PreguntaDTO, Pregunta> {

    @Mapping(source = "usuario.id", target = "usuarioId")

    @Mapping(source = "exposicion.id", target = "exposicionId")
    PreguntaDTO toDto(Pregunta pregunta); 

    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(target = "charlas", ignore = true)

    @Mapping(source = "exposicionId", target = "exposicion")
    Pregunta toEntity(PreguntaDTO preguntaDTO); 
    default Pregunta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pregunta pregunta = new Pregunta();
        pregunta.setId(id);
        return pregunta;
    }
}
