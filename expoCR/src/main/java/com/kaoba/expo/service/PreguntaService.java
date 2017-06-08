package com.kaoba.expo.service;

import com.kaoba.expo.domain.Pregunta;
import com.kaoba.expo.repository.PreguntaRepository;
import com.kaoba.expo.service.dto.PreguntaDTO;
import com.kaoba.expo.service.mapper.PreguntaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Pregunta.
 */
@Service
@Transactional
public class PreguntaService {

    private final Logger log = LoggerFactory.getLogger(PreguntaService.class);

    private final PreguntaRepository preguntaRepository;

    private final PreguntaMapper preguntaMapper;

    public PreguntaService(PreguntaRepository preguntaRepository, PreguntaMapper preguntaMapper) {
        this.preguntaRepository = preguntaRepository;
        this.preguntaMapper = preguntaMapper;
    }

    /**
     * Save a pregunta.
     *
     * @param preguntaDTO the entity to save
     * @return the persisted entity
     */
    public PreguntaDTO save(PreguntaDTO preguntaDTO) {
        log.debug("Request to save Pregunta : {}", preguntaDTO);
        Pregunta pregunta = preguntaMapper.toEntity(preguntaDTO);
        pregunta = preguntaRepository.save(pregunta);
        return preguntaMapper.toDto(pregunta);
    }

    /**
     *  Get all the preguntas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PreguntaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Preguntas");
        return preguntaRepository.findAll(pageable)
            .map(preguntaMapper::toDto);
    }

    /**
     *  Get one pregunta by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PreguntaDTO findOne(Long id) {
        log.debug("Request to get Pregunta : {}", id);
        Pregunta pregunta = preguntaRepository.findOne(id);
        return preguntaMapper.toDto(pregunta);
    }

    /**
     *  Delete the  pregunta by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pregunta : {}", id);
        preguntaRepository.delete(id);
    }
}
