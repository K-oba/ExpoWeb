package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.ClickDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Click and its DTO ClickDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClickMapper extends EntityMapper <ClickDTO, Click> {
    
    @Mapping(target = "stand", ignore = true)
    @Mapping(target = "subCategoria", ignore = true)
    @Mapping(target = "exposicion", ignore = true)
    Click toEntity(ClickDTO clickDTO); 
    default Click fromId(Long id) {
        if (id == null) {
            return null;
        }
        Click click = new Click();
        click.setId(id);
        return click;
    }
}
