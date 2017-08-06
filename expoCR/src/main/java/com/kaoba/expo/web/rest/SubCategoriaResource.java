package com.kaoba.expo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoba.expo.service.SubCategoriaService;
import com.kaoba.expo.web.rest.util.HeaderUtil;
import com.kaoba.expo.web.rest.util.PaginationUtil;
import com.kaoba.expo.service.dto.SubCategoriaDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SubCategoria.
 */
@RestController
@RequestMapping("/api")
public class SubCategoriaResource {

    private final Logger log = LoggerFactory.getLogger(SubCategoriaResource.class);

    private static final String ENTITY_NAME = "subCategoria";

    private final SubCategoriaService subCategoriaService;

    public SubCategoriaResource(SubCategoriaService subCategoriaService) {
        this.subCategoriaService = subCategoriaService;
    }

    /**
     * POST  /sub-categorias : Create a new subCategoria.
     *
     * @param subCategoriaDTO the subCategoriaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subCategoriaDTO, or with status 400 (Bad Request) if the subCategoria has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sub-categorias")
    @Timed
    public ResponseEntity<SubCategoriaDTO> createSubCategoria(@RequestBody SubCategoriaDTO subCategoriaDTO) throws URISyntaxException {
        log.debug("REST request to save SubCategoria : {}", subCategoriaDTO);
        if (subCategoriaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new subCategoria cannot already have an ID")).body(null);
        }
        SubCategoriaDTO result = subCategoriaService.save(subCategoriaDTO);
        return ResponseEntity.created(new URI("/api/sub-categorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sub-categorias : Updates an existing subCategoria.
     *
     * @param subCategoriaDTO the subCategoriaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subCategoriaDTO,
     * or with status 400 (Bad Request) if the subCategoriaDTO is not valid,
     * or with status 500 (Internal Server Error) if the subCategoriaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sub-categorias")
    @Timed
    public ResponseEntity<SubCategoriaDTO> updateSubCategoria(@RequestBody SubCategoriaDTO subCategoriaDTO) throws URISyntaxException {
        log.debug("REST request to update SubCategoria : {}", subCategoriaDTO);
        if (subCategoriaDTO.getId() == null) {
            return createSubCategoria(subCategoriaDTO);
        }
        SubCategoriaDTO result = subCategoriaService.save(subCategoriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subCategoriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sub-categorias : get all the subCategorias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of subCategorias in body
     */
    @GetMapping("/sub-categorias")
    @Timed
    public ResponseEntity<List<SubCategoriaDTO>> getAllSubCategorias(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SubCategorias");
        Page<SubCategoriaDTO> page = subCategoriaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sub-categorias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sub-categorias/:id : get the "id" subCategoria.
     *
     * @param id the id of the subCategoriaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subCategoriaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sub-categorias/{id}")
    @Timed
    public ResponseEntity<SubCategoriaDTO> getSubCategoria(@PathVariable Long id) {
        log.debug("REST request to get SubCategoria : {}", id);
        SubCategoriaDTO subCategoriaDTO = subCategoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(subCategoriaDTO));
    }

    /**
     * GET  /sub-categorias/:expoId : get the "id" subCategoria.
     *
     * @param expoId the id of the expo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subCategoriaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sub-categoriaByExpo/{expoId}")
    @Timed
    public ResponseEntity<List<SubCategoriaDTO>> getSubCategoriaByExpo(@PathVariable Long expoId) {
        log.debug("REST request to get SubCategoria : {}", expoId);
        List<SubCategoriaDTO> subCategoriaDTO = subCategoriaService.findByExpo(expoId);
        return new ResponseEntity<>(subCategoriaDTO, null, HttpStatus.OK);
    }

    /**
     * DELETE  /sub-categorias/:id : delete the "id" subCategoria.
     *
     * @param id the id of the subCategoriaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sub-categorias/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubCategoria(@PathVariable Long id) {
        log.debug("REST request to delete SubCategoria : {}", id);
        subCategoriaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
