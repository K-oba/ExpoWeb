package com.kaoba.expo.service;

import com.kaoba.expo.domain.Amenidades;
import com.kaoba.expo.repository.AmenidadesRepository;
import com.kaoba.expo.service.dto.AmenidadesDTO;
import com.kaoba.expo.service.mapper.AmenidadesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Amenidades.
 */
@Service
@Transactional
public class AmenidadesService {

    private final Logger log = LoggerFactory.getLogger(AmenidadesService.class);

    private final AmenidadesRepository amenidadesRepository;

    private final AmenidadesMapper amenidadesMapper;

    public AmenidadesService(AmenidadesRepository amenidadesRepository, AmenidadesMapper amenidadesMapper) {
        this.amenidadesRepository = amenidadesRepository;
        this.amenidadesMapper = amenidadesMapper;
    }

    /**
     * Save a amenidades.
     *
     * @param amenidadesDTO the entity to save
     * @return the persisted entity
     */
    public AmenidadesDTO save(AmenidadesDTO amenidadesDTO) {
        log.debug("Request to save Amenidades : {}", amenidadesDTO);
        Amenidades amenidades = amenidadesMapper.toEntity(amenidadesDTO);
        amenidades = amenidadesRepository.save(amenidades);
        return amenidadesMapper.toDto(amenidades);
    }

    /**
     *  Get all the amenidades.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AmenidadesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Amenidades");
        return amenidadesRepository.findAll(pageable)
            .map(amenidadesMapper::toDto);
    }

    /**
     *  Get one amenidades by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AmenidadesDTO findOne(Long id) {
        log.debug("Request to get Amenidades : {}", id);
        Amenidades amenidades = amenidadesRepository.findOne(id);
        return amenidadesMapper.toDto(amenidades);
    }

    /**
     *  Delete the  amenidades by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Amenidades : {}", id);
        amenidadesRepository.delete(id);
    }
}
