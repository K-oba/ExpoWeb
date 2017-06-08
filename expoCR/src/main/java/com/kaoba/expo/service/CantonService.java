package com.kaoba.expo.service;

import com.kaoba.expo.domain.Canton;
import com.kaoba.expo.repository.CantonRepository;
import com.kaoba.expo.service.dto.CantonDTO;
import com.kaoba.expo.service.mapper.CantonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Canton.
 */
@Service
@Transactional
public class CantonService {

    private final Logger log = LoggerFactory.getLogger(CantonService.class);

    private final CantonRepository cantonRepository;

    private final CantonMapper cantonMapper;

    public CantonService(CantonRepository cantonRepository, CantonMapper cantonMapper) {
        this.cantonRepository = cantonRepository;
        this.cantonMapper = cantonMapper;
    }

    /**
     * Save a canton.
     *
     * @param cantonDTO the entity to save
     * @return the persisted entity
     */
    public CantonDTO save(CantonDTO cantonDTO) {
        log.debug("Request to save Canton : {}", cantonDTO);
        Canton canton = cantonMapper.toEntity(cantonDTO);
        canton = cantonRepository.save(canton);
        return cantonMapper.toDto(canton);
    }

    /**
     *  Get all the cantons.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CantonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cantons");
        return cantonRepository.findAll(pageable)
            .map(cantonMapper::toDto);
    }

    /**
     *  Get one canton by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CantonDTO findOne(Long id) {
        log.debug("Request to get Canton : {}", id);
        Canton canton = cantonRepository.findOne(id);
        return cantonMapper.toDto(canton);
    }

    /**
     *  Delete the  canton by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Canton : {}", id);
        cantonRepository.delete(id);
    }
}
