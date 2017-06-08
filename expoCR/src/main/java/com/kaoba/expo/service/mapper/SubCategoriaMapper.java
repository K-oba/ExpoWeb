package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.SubCategoriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SubCategoria and its DTO SubCategoriaDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoriaMapper.class, BrouchureMapper.class, ClickMapper.class, BeaconMapper.class, })
public interface SubCategoriaMapper extends EntityMapper <SubCategoriaDTO, SubCategoria> {

    @Mapping(source = "categoria.id", target = "categoriaId")

    @Mapping(source = "brouchure.id", target = "brouchureId")

    @Mapping(source = "click.id", target = "clickId")

    @Mapping(source = "beacon.id", target = "beaconId")
    SubCategoriaDTO toDto(SubCategoria subCategoria); 

    @Mapping(source = "categoriaId", target = "categoria")

    @Mapping(source = "brouchureId", target = "brouchure")

    @Mapping(source = "clickId", target = "click")

    @Mapping(source = "beaconId", target = "beacon")
    SubCategoria toEntity(SubCategoriaDTO subCategoriaDTO); 
    default SubCategoria fromId(Long id) {
        if (id == null) {
            return null;
        }
        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setId(id);
        return subCategoria;
    }
}
