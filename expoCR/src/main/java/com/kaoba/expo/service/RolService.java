package com.kaoba.expo.service;

import com.kaoba.expo.domain.Rol;
import com.kaoba.expo.repository.RolRepository;
import com.kaoba.expo.service.dto.RolDTO;
import com.kaoba.expo.service.mapper.RolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Rol.
 */
@Service
@Transactional
public class RolService {

    private final Logger log = LoggerFactory.getLogger(RolService.class);

    private final RolRepository rolRepository;

    private final RolMapper rolMapper;

    public RolService(RolRepository rolRepository, RolMapper rolMapper) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
    }

    /**
     * Save a rol.
     *
     * @param rolDTO the entity to save
     * @return the persisted entity
     */
    public RolDTO save(RolDTO rolDTO) {
        log.debug("Request to save Rol : {}", rolDTO);
        Rol rol = rolMapper.toEntity(rolDTO);
        rol = rolRepository.save(rol);
        return rolMapper.toDto(rol);
    }

    /**
     *  Get all the rols.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RolDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rols");
        return rolRepository.findAll(pageable)
            .map(rolMapper::toDto);
    }

    /**
     *  Get one rol by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public RolDTO findOne(Long id) {
        log.debug("Request to get Rol : {}", id);
        Rol rol = rolRepository.findOne(id);
        return rolMapper.toDto(rol);
    }

    /**
     *  Delete the  rol by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Rol : {}", id);
        rolRepository.delete(id);
    }
}
