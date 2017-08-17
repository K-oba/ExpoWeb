package com.kaoba.expo.service;

import com.kaoba.expo.domain.HistorialUsuariosExpo;
import com.kaoba.expo.repository.HistorialUsuariosExpoRepository;
import com.kaoba.expo.service.dto.HistorialUsuariosExpoDTO;
import com.kaoba.expo.service.mapper.HistorialUsuariosExpoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;


/**
 * Service Implementation for managing HistorialUsuariosExpo.
 */
@Service
@Transactional
public class HistorialUsuariosExpoService {

    private final Logger log = LoggerFactory.getLogger(HistorialUsuariosExpoService.class);

    private final HistorialUsuariosExpoRepository historialUsuariosExpoRepository;

    private final HistorialUsuariosExpoMapper historialUsuariosExpoMapper;

    public HistorialUsuariosExpoService(HistorialUsuariosExpoRepository historialUsuariosExpoRepository, HistorialUsuariosExpoMapper historialUsuariosExpoMapper) {
        this.historialUsuariosExpoRepository = historialUsuariosExpoRepository;
        this.historialUsuariosExpoMapper = historialUsuariosExpoMapper;
    }

    /**
     * Save a historialUsuariosExpo.
     *
     * @param historialUsuariosExpoDTO the entity to save
     * @return the persisted entity
     */
    public HistorialUsuariosExpoDTO save(HistorialUsuariosExpoDTO historialUsuariosExpoDTO) {
        log.debug("Request to save HistorialUsuariosExpo : {}", historialUsuariosExpoDTO);
        HistorialUsuariosExpo historialUsuariosExpo = historialUsuariosExpoMapper.toEntity(historialUsuariosExpoDTO);
        if (historialUsuariosExpoRepository.findByDeviceId(historialUsuariosExpoDTO.getDeviceId())==null) {
            historialUsuariosExpo.setFecha(Instant.now());
            historialUsuariosExpo = historialUsuariosExpoRepository.save(historialUsuariosExpo);
        }
        return historialUsuariosExpoMapper.toDto(historialUsuariosExpo);
    }

    /**
     *  Get all the historialUsuariosExpos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<HistorialUsuariosExpoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HistorialUsuariosExpos");
        return historialUsuariosExpoRepository.findAll(pageable)
            .map(historialUsuariosExpoMapper::toDto);
    }

    /**
     *  Get one historialUsuariosExpo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public HistorialUsuariosExpoDTO findOne(Long id) {
        log.debug("Request to get HistorialUsuariosExpo : {}", id);
        HistorialUsuariosExpo historialUsuariosExpo = historialUsuariosExpoRepository.findOne(id);
        return historialUsuariosExpoMapper.toDto(historialUsuariosExpo);
    }

    /**
     *  Delete the  historialUsuariosExpo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete HistorialUsuariosExpo : {}", id);
        historialUsuariosExpoRepository.delete(id);
    }
}
