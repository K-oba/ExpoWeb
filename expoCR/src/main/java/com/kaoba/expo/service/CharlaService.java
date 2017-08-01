package com.kaoba.expo.service;

import com.kaoba.expo.domain.Charla;
import com.kaoba.expo.repository.CharlaRepository;
import com.kaoba.expo.service.dto.CharlaDTO;
import com.kaoba.expo.service.mapper.CharlaMapper;
import java.util.LinkedList;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.StreamSupport;
import java.util.LinkedList;
import java.util.List;

/**
 * Service Implementation for managing Charla.
 */
@Service
@Transactional
public class CharlaService {

    private final Logger log = LoggerFactory.getLogger(CharlaService.class);

    private final CharlaRepository charlaRepository;

    private final CharlaMapper charlaMapper;

    public CharlaService(CharlaRepository charlaRepository, CharlaMapper charlaMapper) {
        this.charlaRepository = charlaRepository;
        this.charlaMapper = charlaMapper;
    }

    /**
     * Save a charla.
     *
     * @param charlaDTO the entity to save
     * @return the persisted entity
     */
    public CharlaDTO save(CharlaDTO charlaDTO) {
        log.debug("Request to save Charla : {}", charlaDTO);
        Charla charla = charlaMapper.toEntity(charlaDTO);
        charla = charlaRepository.save(charla);
        return charlaMapper.toDto(charla);
    }

    /**
     *  Get all the charlas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CharlaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Charlas");
        return charlaRepository.findAll(pageable)
            .map(charlaMapper::toDto);
    }

    /**
     *  Get one charla by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CharlaDTO findOne(Long id) {
        log.debug("Request to get Charla : {}", id);
        Charla charla = charlaRepository.findOneWithEagerRelationships(id);
        return charlaMapper.toDto(charla);
    }

    /**
     *  Delete the  charla by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Charla : {}", expoId);
        charlaRepository.delete(id);
    }
    
    public List<CharlaDTO> getCharlaByExpo(Long expoId){
        //Charla charla = charlaRepository.findByExpoId(expoId);
        return StreamSupport
            .stream(charlaRepository.findAll().spliterator(), false)
            .filter(charla -> charla.getExposicion().getId() == expoId)
            .map(charlaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
