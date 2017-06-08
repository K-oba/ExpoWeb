package com.kaoba.expo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoba.expo.service.BrouchureService;
import com.kaoba.expo.web.rest.util.HeaderUtil;
import com.kaoba.expo.web.rest.util.PaginationUtil;
import com.kaoba.expo.service.dto.BrouchureDTO;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Brouchure.
 */
@RestController
@RequestMapping("/api")
public class BrouchureResource {

    private final Logger log = LoggerFactory.getLogger(BrouchureResource.class);

    private static final String ENTITY_NAME = "brouchure";

    private final BrouchureService brouchureService;

    public BrouchureResource(BrouchureService brouchureService) {
        this.brouchureService = brouchureService;
    }

    /**
     * POST  /brouchures : Create a new brouchure.
     *
     * @param brouchureDTO the brouchureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new brouchureDTO, or with status 400 (Bad Request) if the brouchure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/brouchures")
    @Timed
    public ResponseEntity<BrouchureDTO> createBrouchure(@RequestBody BrouchureDTO brouchureDTO) throws URISyntaxException {
        log.debug("REST request to save Brouchure : {}", brouchureDTO);
        if (brouchureDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new brouchure cannot already have an ID")).body(null);
        }
        BrouchureDTO result = brouchureService.save(brouchureDTO);
        return ResponseEntity.created(new URI("/api/brouchures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /brouchures : Updates an existing brouchure.
     *
     * @param brouchureDTO the brouchureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated brouchureDTO,
     * or with status 400 (Bad Request) if the brouchureDTO is not valid,
     * or with status 500 (Internal Server Error) if the brouchureDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/brouchures")
    @Timed
    public ResponseEntity<BrouchureDTO> updateBrouchure(@RequestBody BrouchureDTO brouchureDTO) throws URISyntaxException {
        log.debug("REST request to update Brouchure : {}", brouchureDTO);
        if (brouchureDTO.getId() == null) {
            return createBrouchure(brouchureDTO);
        }
        BrouchureDTO result = brouchureService.save(brouchureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, brouchureDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /brouchures : get all the brouchures.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of brouchures in body
     */
    @GetMapping("/brouchures")
    @Timed
    public ResponseEntity<List<BrouchureDTO>> getAllBrouchures(@ApiParam Pageable pageable, @RequestParam(required = false) String filter) {
        if ("stand-is-null".equals(filter)) {
            log.debug("REST request to get all Brouchures where stand is null");
            return new ResponseEntity<>(brouchureService.findAllWhereStandIsNull(),
                    HttpStatus.OK);
        }
        if ("subcategoria-is-null".equals(filter)) {
            log.debug("REST request to get all Brouchures where subCategoria is null");
            return new ResponseEntity<>(brouchureService.findAllWhereSubCategoriaIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Brouchures");
        Page<BrouchureDTO> page = brouchureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/brouchures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /brouchures/:id : get the "id" brouchure.
     *
     * @param id the id of the brouchureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the brouchureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/brouchures/{id}")
    @Timed
    public ResponseEntity<BrouchureDTO> getBrouchure(@PathVariable Long id) {
        log.debug("REST request to get Brouchure : {}", id);
        BrouchureDTO brouchureDTO = brouchureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(brouchureDTO));
    }

    /**
     * DELETE  /brouchures/:id : delete the "id" brouchure.
     *
     * @param id the id of the brouchureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/brouchures/{id}")
    @Timed
    public ResponseEntity<Void> deleteBrouchure(@PathVariable Long id) {
        log.debug("REST request to delete Brouchure : {}", id);
        brouchureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
