package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.ExposicionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Exposicion and its DTO ExposicionDTO.
 */
@Mapper(componentModel = "spring", uses = {DistritoMapper.class, CategoriaMapper.class, AmenidadesMapper.class, ClickMapper.class, UsuarioMapper.class, })
public interface ExposicionMapper extends EntityMapper <ExposicionDTO, Exposicion> {

    @Mapping(source = "distrito.id", target = "distritoId")

    @Mapping(source = "categoria.id", target = "categoriaId")

    @Mapping(source = "click.id", target = "clickId")

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.nombre", target = "usuarioNombre")
    ExposicionDTO toDto(Exposicion exposicion); 

    @Mapping(source = "distritoId", target = "distrito")

    @Mapping(source = "categoriaId", target = "categoria")
    @Mapping(target = "charlas", ignore = true)
    @Mapping(target = "beacons", ignore = true)
    @Mapping(target = "timelines", ignore = true)

    @Mapping(source = "clickId", target = "click")
    @Mapping(target = "preguntas", ignore = true)

    @Mapping(source = "usuarioId", target = "usuario")
    Exposicion toEntity(ExposicionDTO exposicionDTO); 
    default Exposicion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exposicion exposicion = new Exposicion();
        exposicion.setId(id);
        return exposicion;
    }
}
