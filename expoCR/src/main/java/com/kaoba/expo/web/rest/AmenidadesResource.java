package com.kaoba.expo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoba.expo.service.AmenidadesService;
import com.kaoba.expo.web.rest.util.HeaderUtil;
import com.kaoba.expo.web.rest.util.PaginationUtil;
import com.kaoba.expo.service.dto.AmenidadesDTO;
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
 * REST controller for managing Amenidades.
 */
@RestController
@RequestMapping("/api")
public class AmenidadesResource {

    private final Logger log = LoggerFactory.getLogger(AmenidadesResource.class);

    private static final String ENTITY_NAME = "amenidades";

    private final AmenidadesService amenidadesService;

    public AmenidadesResource(AmenidadesService amenidadesService) {
        this.amenidadesService = amenidadesService;
    }

    /**
     * POST  /amenidades : Create a new amenidades.
     *
     * @param amenidadesDTO the amenidadesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new amenidadesDTO, or with status 400 (Bad Request) if the amenidades has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/amenidades")
    @Timed
    public ResponseEntity<AmenidadesDTO> createAmenidades(@RequestBody AmenidadesDTO amenidadesDTO) throws URISyntaxException {
        log.debug("REST request to save Amenidades : {}", amenidadesDTO);
        if (amenidadesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new amenidades cannot already have an ID")).body(null);
        }
        AmenidadesDTO result = amenidadesService.save(amenidadesDTO);
        return ResponseEntity.created(new URI("/api/amenidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /amenidades : Updates an existing amenidades.
     *
     * @param amenidadesDTO the amenidadesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated amenidadesDTO,
     * or with status 400 (Bad Request) if the amenidadesDTO is not valid,
     * or with status 500 (Internal Server Error) if the amenidadesDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/amenidades")
    @Timed
    public ResponseEntity<AmenidadesDTO> updateAmenidades(@RequestBody AmenidadesDTO amenidadesDTO) throws URISyntaxException {
        log.debug("REST request to update Amenidades : {}", amenidadesDTO);
        if (amenidadesDTO.getId() == null) {
            return createAmenidades(amenidadesDTO);
        }
        AmenidadesDTO result = amenidadesService.save(amenidadesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, amenidadesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /amenidades : get all the amenidades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of amenidades in body
     */
    @GetMapping("/amenidades")
    @Timed
    public ResponseEntity<List<AmenidadesDTO>> getAllAmenidades(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Amenidades");
        Page<AmenidadesDTO> page = amenidadesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/amenidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /amenidades/:id : get the "id" amenidades.
     *
     * @param id the id of the amenidadesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the amenidadesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/amenidades/{id}")
    @Timed
    public ResponseEntity<AmenidadesDTO> getAmenidades(@PathVariable Long id) {
        log.debug("REST request to get Amenidades : {}", id);
        AmenidadesDTO amenidadesDTO = amenidadesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(amenidadesDTO));
    }

    /**
     * DELETE  /amenidades/:id : delete the "id" amenidades.
     *
     * @param id the id of the amenidadesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/amenidades/{id}")
    @Timed
    public ResponseEntity<Void> deleteAmenidades(@PathVariable Long id) {
        log.debug("REST request to delete Amenidades : {}", id);
        amenidadesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
