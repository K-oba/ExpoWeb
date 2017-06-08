package com.kaoba.expo.service;

import com.kaoba.expo.domain.Distrito;
import com.kaoba.expo.repository.DistritoRepository;
import com.kaoba.expo.service.dto.DistritoDTO;
import com.kaoba.expo.service.mapper.DistritoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Distrito.
 */
@Service
@Transactional
public class DistritoService {

    private final Logger log = LoggerFactory.getLogger(DistritoService.class);

    private final DistritoRepository distritoRepository;

    private final DistritoMapper distritoMapper;

    public DistritoService(DistritoRepository distritoRepository, DistritoMapper distritoMapper) {
        this.distritoRepository = distritoRepository;
        this.distritoMapper = distritoMapper;
    }

    /**
     * Save a distrito.
     *
     * @param distritoDTO the entity to save
     * @return the persisted entity
     */
    public DistritoDTO save(DistritoDTO distritoDTO) {
        log.debug("Request to save Distrito : {}", distritoDTO);
        Distrito distrito = distritoMapper.toEntity(distritoDTO);
        distrito = distritoRepository.save(distrito);
        return distritoMapper.toDto(distrito);
    }

    /**
     *  Get all the distritos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DistritoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Distritos");
        return distritoRepository.findAll(pageable)
            .map(distritoMapper::toDto);
    }

    /**
     *  Get one distrito by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DistritoDTO findOne(Long id) {
        log.debug("Request to get Distrito : {}", id);
        Distrito distrito = distritoRepository.findOne(id);
        return distritoMapper.toDto(distrito);
    }

    /**
     *  Delete the  distrito by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Distrito : {}", id);
        distritoRepository.delete(id);
    }
}
