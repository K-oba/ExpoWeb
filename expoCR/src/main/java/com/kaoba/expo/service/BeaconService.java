package com.kaoba.expo.service;

import com.kaoba.expo.domain.Beacon;
import com.kaoba.expo.repository.BeaconRepository;
import com.kaoba.expo.service.dto.BeaconDTO;
import com.kaoba.expo.service.mapper.BeaconMapper;
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
 * Service Implementation for managing Beacon.
 */
@Service
@Transactional
public class BeaconService {

    private final Logger log = LoggerFactory.getLogger(BeaconService.class);

    private final BeaconRepository beaconRepository;

    private final BeaconMapper beaconMapper;

    public BeaconService(BeaconRepository beaconRepository, BeaconMapper beaconMapper) {
        this.beaconRepository = beaconRepository;
        this.beaconMapper = beaconMapper;
    }

    /**
     * Save a beacon.
     *
     * @param beaconDTO the entity to save
     * @return the persisted entity
     */
    public BeaconDTO save(BeaconDTO beaconDTO) {
        log.debug("Request to save Beacon : {}", beaconDTO);
        Beacon beacon = beaconMapper.toEntity(beaconDTO);
        beacon = beaconRepository.save(beacon);
        return beaconMapper.toDto(beacon);
    }

    /**
     *  Get all the beacons.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BeaconDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Beacons");
        return beaconRepository.findAll(pageable)
            .map(beaconMapper::toDto);
    }


    /**
     *  get all the beacons where Stand is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<BeaconDTO> findAllWhereStandIsNull() {
        log.debug("Request to get all beacons where Stand is null");
        return StreamSupport
            .stream(beaconRepository.findAll().spliterator(), false)
            .filter(beacon -> beacon.getStand() == null)
            .map(beaconMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the beacons where SubCategoria is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<BeaconDTO> findAllWhereSubCategoriaIsNull() {
        log.debug("Request to get all beacons where SubCategoria is null");
        return StreamSupport
            .stream(beaconRepository.findAll().spliterator(), false)
            .filter(beacon -> beacon.getSubCategoria() == null)
            .map(beaconMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one beacon by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BeaconDTO findOne(Long id) {
        log.debug("Request to get Beacon : {}", id);
        Beacon beacon = beaconRepository.findOne(id);
        return beaconMapper.toDto(beacon);
    }

    /**
     *  Delete the  beacon by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Beacon : {}", id);
        beaconRepository.delete(id);
    }
}
