package com.kaoba.expo.service;

import com.kaoba.expo.domain.SubCategoria;
import com.kaoba.expo.repository.SubCategoriaRepository;
import com.kaoba.expo.service.dto.SubCategoriaDTO;
import com.kaoba.expo.service.mapper.SubCategoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SubCategoria.
 */
@Service
@Transactional
public class SubCategoriaService {

    private final Logger log = LoggerFactory.getLogger(SubCategoriaService.class);

    private final SubCategoriaRepository subCategoriaRepository;

    private final SubCategoriaMapper subCategoriaMapper;

    public SubCategoriaService(SubCategoriaRepository subCategoriaRepository, SubCategoriaMapper subCategoriaMapper) {
        this.subCategoriaRepository = subCategoriaRepository;
        this.subCategoriaMapper = subCategoriaMapper;
    }

    /**
     * Save a subCategoria.
     *
     * @param subCategoriaDTO the entity to save
     * @return the persisted entity
     */
    public SubCategoriaDTO save(SubCategoriaDTO subCategoriaDTO) {
        log.debug("Request to save SubCategoria : {}", subCategoriaDTO);
        SubCategoria subCategoria = subCategoriaMapper.toEntity(subCategoriaDTO);
        subCategoria = subCategoriaRepository.save(subCategoria);
        return subCategoriaMapper.toDto(subCategoria);
    }

    /**
     *  Get all the subCategorias.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SubCategoriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubCategorias");
        return subCategoriaRepository.findAll(pageable)
            .map(subCategoriaMapper::toDto);
    }

    /**
     *  Get one subCategoria by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SubCategoriaDTO findOne(Long id) {
        log.debug("Request to get SubCategoria : {}", id);
        SubCategoria subCategoria = subCategoriaRepository.findOne(id);
        return subCategoriaMapper.toDto(subCategoria);
    }

    /**
     *  Delete the  subCategoria by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SubCategoria : {}", id);
        subCategoriaRepository.delete(id);
    }
}
