package com.kaoba.expo.service;

import com.kaoba.expo.domain.Stand;
import com.kaoba.expo.repository.StandRepository;
import com.kaoba.expo.service.dto.StandDTO;
import com.kaoba.expo.service.mapper.StandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Stand.
 */
@Service
@Transactional
public class StandService {

    private final Logger log = LoggerFactory.getLogger(StandService.class);

    private final StandRepository standRepository;

    private final StandMapper standMapper;

    public StandService(StandRepository standRepository, StandMapper standMapper) {
        this.standRepository = standRepository;
        this.standMapper = standMapper;
    }

    /**
     * Save a stand.
     *
     * @param standDTO the entity to save
     * @return the persisted entity
     */
    public StandDTO save(StandDTO standDTO) {
        log.debug("Request to save Stand : {}", standDTO);
        Stand stand = standMapper.toEntity(standDTO);
        stand = standRepository.save(stand);
        return standMapper.toDto(stand);
    }

    /**
     *  Get all the stands.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StandDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Stands");
        return standRepository.findAll(pageable)
            .map(standMapper::toDto);
    }


    /**
     *  get all the stands where Usuario is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<StandDTO> findAllWhereUsuarioIsNull() {
        log.debug("Request to get all stands where Usuario is null");
        return StreamSupport
            .stream(standRepository.findAll().spliterator(), false)
            .filter(stand -> stand.getUsuario() == null)
            .map(standMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one stand by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public StandDTO findOne(Long id) {
        log.debug("Request to get Stand : {}", id);
        Stand stand = standRepository.findOne(id);
        return standMapper.toDto(stand);
    }

    /**
     *  Delete the  stand by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Stand : {}", id);
        standRepository.delete(id);
    }
}
