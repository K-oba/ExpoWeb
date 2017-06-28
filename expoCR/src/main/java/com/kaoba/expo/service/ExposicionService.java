package com.kaoba.expo.service;

import com.kaoba.expo.domain.Exposicion;
import com.kaoba.expo.repository.ExposicionRepository;
import com.kaoba.expo.service.dto.ExposicionDTO;
import com.kaoba.expo.service.mapper.ExposicionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing Exposicion.
 */
@Service
@Transactional
public class ExposicionService {

    private final Logger log = LoggerFactory.getLogger(ExposicionService.class);

    private final ExposicionRepository exposicionRepository;

    private final ExposicionMapper exposicionMapper;

    public ExposicionService(ExposicionRepository exposicionRepository, ExposicionMapper exposicionMapper) {
        this.exposicionRepository = exposicionRepository;
        this.exposicionMapper = exposicionMapper;
    }

    /**
     * Save a exposicion.
     *
     * @param exposicionDTO the entity to save
     * @return the persisted entity
     */
    public ExposicionDTO save(ExposicionDTO exposicionDTO) {
        log.debug("Request to save Exposicion : {}", exposicionDTO);
        Exposicion exposicion = exposicionMapper.toEntity(exposicionDTO);
        exposicion = exposicionRepository.save(exposicion);
        return exposicionMapper.toDto(exposicion);
    }

    /**
     *  Get all the exposicions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExposicionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Exposicions");
        return exposicionRepository.findAll(pageable)
            .map(exposicionMapper::toDto);
    }

    /**
     *  Get all the exposicions.
     *
     *  @param userId the user id
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ExposicionDTO> findAllByUser(Long userId) {
        log.debug("Request to get all Exposicions");
        List<Exposicion> exposicion = exposicionRepository.findByUsuarioId(userId);
        return exposicionMapper.toDto(exposicion);
    }

    /**
     *  Get one exposicion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ExposicionDTO findOne(Long id) {
        log.debug("Request to get Exposicion : {}", id);
        Exposicion exposicion = exposicionRepository.findOneWithEagerRelationships(id);
        return exposicionMapper.toDto(exposicion);
    }

    /**
     *  Delete the  exposicion by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Exposicion : {}", id);
        exposicionRepository.delete(id);
    }
}
