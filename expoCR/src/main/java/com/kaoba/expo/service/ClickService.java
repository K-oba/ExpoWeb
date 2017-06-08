package com.kaoba.expo.service;

import com.kaoba.expo.domain.Click;
import com.kaoba.expo.repository.ClickRepository;
import com.kaoba.expo.service.dto.ClickDTO;
import com.kaoba.expo.service.mapper.ClickMapper;
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
 * Service Implementation for managing Click.
 */
@Service
@Transactional
public class ClickService {

    private final Logger log = LoggerFactory.getLogger(ClickService.class);

    private final ClickRepository clickRepository;

    private final ClickMapper clickMapper;

    public ClickService(ClickRepository clickRepository, ClickMapper clickMapper) {
        this.clickRepository = clickRepository;
        this.clickMapper = clickMapper;
    }

    /**
     * Save a click.
     *
     * @param clickDTO the entity to save
     * @return the persisted entity
     */
    public ClickDTO save(ClickDTO clickDTO) {
        log.debug("Request to save Click : {}", clickDTO);
        Click click = clickMapper.toEntity(clickDTO);
        click = clickRepository.save(click);
        return clickMapper.toDto(click);
    }

    /**
     *  Get all the clicks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ClickDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clicks");
        return clickRepository.findAll(pageable)
            .map(clickMapper::toDto);
    }


    /**
     *  get all the clicks where Stand is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ClickDTO> findAllWhereStandIsNull() {
        log.debug("Request to get all clicks where Stand is null");
        return StreamSupport
            .stream(clickRepository.findAll().spliterator(), false)
            .filter(click -> click.getStand() == null)
            .map(clickMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the clicks where SubCategoria is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ClickDTO> findAllWhereSubCategoriaIsNull() {
        log.debug("Request to get all clicks where SubCategoria is null");
        return StreamSupport
            .stream(clickRepository.findAll().spliterator(), false)
            .filter(click -> click.getSubCategoria() == null)
            .map(clickMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the clicks where Exposicion is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ClickDTO> findAllWhereExposicionIsNull() {
        log.debug("Request to get all clicks where Exposicion is null");
        return StreamSupport
            .stream(clickRepository.findAll().spliterator(), false)
            .filter(click -> click.getExposicion() == null)
            .map(clickMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one click by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ClickDTO findOne(Long id) {
        log.debug("Request to get Click : {}", id);
        Click click = clickRepository.findOne(id);
        return clickMapper.toDto(click);
    }

    /**
     *  Delete the  click by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Click : {}", id);
        clickRepository.delete(id);
    }
}
