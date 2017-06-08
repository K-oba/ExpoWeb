package com.kaoba.expo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoba.expo.service.CantonService;
import com.kaoba.expo.web.rest.util.HeaderUtil;
import com.kaoba.expo.web.rest.util.PaginationUtil;
import com.kaoba.expo.service.dto.CantonDTO;
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
 * REST controller for managing Canton.
 */
@RestController
@RequestMapping("/api")
public class CantonResource {

    private final Logger log = LoggerFactory.getLogger(CantonResource.class);

    private static final String ENTITY_NAME = "canton";

    private final CantonService cantonService;

    public CantonResource(CantonService cantonService) {
        this.cantonService = cantonService;
    }

    /**
     * POST  /cantons : Create a new canton.
     *
     * @param cantonDTO the cantonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cantonDTO, or with status 400 (Bad Request) if the canton has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cantons")
    @Timed
    public ResponseEntity<CantonDTO> createCanton(@RequestBody CantonDTO cantonDTO) throws URISyntaxException {
        log.debug("REST request to save Canton : {}", cantonDTO);
        if (cantonDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new canton cannot already have an ID")).body(null);
        }
        CantonDTO result = cantonService.save(cantonDTO);
        return ResponseEntity.created(new URI("/api/cantons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cantons : Updates an existing canton.
     *
     * @param cantonDTO the cantonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cantonDTO,
     * or with status 400 (Bad Request) if the cantonDTO is not valid,
     * or with status 500 (Internal Server Error) if the cantonDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cantons")
    @Timed
    public ResponseEntity<CantonDTO> updateCanton(@RequestBody CantonDTO cantonDTO) throws URISyntaxException {
        log.debug("REST request to update Canton : {}", cantonDTO);
        if (cantonDTO.getId() == null) {
            return createCanton(cantonDTO);
        }
        CantonDTO result = cantonService.save(cantonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cantonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cantons : get all the cantons.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cantons in body
     */
    @GetMapping("/cantons")
    @Timed
    public ResponseEntity<List<CantonDTO>> getAllCantons(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Cantons");
        Page<CantonDTO> page = cantonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cantons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cantons/:id : get the "id" canton.
     *
     * @param id the id of the cantonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cantonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cantons/{id}")
    @Timed
    public ResponseEntity<CantonDTO> getCanton(@PathVariable Long id) {
        log.debug("REST request to get Canton : {}", id);
        CantonDTO cantonDTO = cantonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cantonDTO));
    }

    /**
     * DELETE  /cantons/:id : delete the "id" canton.
     *
     * @param id the id of the cantonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cantons/{id}")
    @Timed
    public ResponseEntity<Void> deleteCanton(@PathVariable Long id) {
        log.debug("REST request to delete Canton : {}", id);
        cantonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
