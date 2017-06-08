package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.UsuarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Usuario and its DTO UsuarioDTO.
 */
@Mapper(componentModel = "spring", uses = {TimelineMapper.class, StandMapper.class, RolMapper.class, })
public interface UsuarioMapper extends EntityMapper <UsuarioDTO, Usuario> {

    @Mapping(source = "stand.id", target = "standId")
    @Mapping(source= "stand.nombre", target = "standNombre")
    @Mapping(source = "rol.id", target = "rolId")
    @Mapping(source = "rol.nombre", target = "rolNombre")
    UsuarioDTO toDto(Usuario usuario);
    @Mapping(target = "preguntas", ignore = true)

    @Mapping(source = "standId", target = "stand")

    @Mapping(source = "rolId", target = "rol")
    Usuario toEntity(UsuarioDTO usuarioDTO);
    default Usuario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}
