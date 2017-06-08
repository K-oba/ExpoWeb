package com.kaoba.expo.service;

import com.kaoba.expo.domain.Brouchure;
import com.kaoba.expo.repository.BrouchureRepository;
import com.kaoba.expo.service.dto.BrouchureDTO;
import com.kaoba.expo.service.mapper.BrouchureMapper;
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
 * Service Implementation for managing Brouchure.
 */
@Service
@Transactional
public class BrouchureService {

    private final Logger log = LoggerFactory.getLogger(BrouchureService.class);

    private final BrouchureRepository brouchureRepository;

    private final BrouchureMapper brouchureMapper;

    public BrouchureService(BrouchureRepository brouchureRepository, BrouchureMapper brouchureMapper) {
        this.brouchureRepository = brouchureRepository;
        this.brouchureMapper = brouchureMapper;
    }

    /**
     * Save a brouchure.
     *
     * @param brouchureDTO the entity to save
     * @return the persisted entity
     */
    public BrouchureDTO save(BrouchureDTO brouchureDTO) {
        log.debug("Request to save Brouchure : {}", brouchureDTO);
        Brouchure brouchure = brouchureMapper.toEntity(brouchureDTO);
        brouchure = brouchureRepository.save(brouchure);
        return brouchureMapper.toDto(brouchure);
    }

    /**
     *  Get all the brouchures.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BrouchureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Brouchures");
        return brouchureRepository.findAll(pageable)
            .map(brouchureMapper::toDto);
    }


    /**
     *  get all the brouchures where Stand is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<BrouchureDTO> findAllWhereStandIsNull() {
        log.debug("Request to get all brouchures where Stand is null");
        return StreamSupport
            .stream(brouchureRepository.findAll().spliterator(), false)
            .filter(brouchure -> brouchure.getStand() == null)
            .map(brouchureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the brouchures where SubCategoria is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<BrouchureDTO> findAllWhereSubCategoriaIsNull() {
        log.debug("Request to get all brouchures where SubCategoria is null");
        return StreamSupport
            .stream(brouchureRepository.findAll().spliterator(), false)
            .filter(brouchure -> brouchure.getSubCategoria() == null)
            .map(brouchureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one brouchure by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BrouchureDTO findOne(Long id) {
        log.debug("Request to get Brouchure : {}", id);
        Brouchure brouchure = brouchureRepository.findOne(id);
        return brouchureMapper.toDto(brouchure);
    }

    /**
     *  Delete the  brouchure by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Brouchure : {}", id);
        brouchureRepository.delete(id);
    }
}
