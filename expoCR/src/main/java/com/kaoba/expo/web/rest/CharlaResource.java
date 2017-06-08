package com.kaoba.expo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoba.expo.service.CharlaService;
import com.kaoba.expo.web.rest.util.HeaderUtil;
import com.kaoba.expo.web.rest.util.PaginationUtil;
import com.kaoba.expo.service.dto.CharlaDTO;
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
 * REST controller for managing Charla.
 */
@RestController
@RequestMapping("/api")
public class CharlaResource {

    private final Logger log = LoggerFactory.getLogger(CharlaResource.class);

    private static final String ENTITY_NAME = "charla";

    private final CharlaService charlaService;

    public CharlaResource(CharlaService charlaService) {
        this.charlaService = charlaService;
    }

    /**
     * POST  /charlas : Create a new charla.
     *
     * @param charlaDTO the charlaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new charlaDTO, or with status 400 (Bad Request) if the charla has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/charlas")
    @Timed
    public ResponseEntity<CharlaDTO> createCharla(@RequestBody CharlaDTO charlaDTO) throws URISyntaxException {
        log.debug("REST request to save Charla : {}", charlaDTO);
        if (charlaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new charla cannot already have an ID")).body(null);
        }
        CharlaDTO result = charlaService.save(charlaDTO);
        return ResponseEntity.created(new URI("/api/charlas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /charlas : Updates an existing charla.
     *
     * @param charlaDTO the charlaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated charlaDTO,
     * or with status 400 (Bad Request) if the charlaDTO is not valid,
     * or with status 500 (Internal Server Error) if the charlaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/charlas")
    @Timed
    public ResponseEntity<CharlaDTO> updateCharla(@RequestBody CharlaDTO charlaDTO) throws URISyntaxException {
        log.debug("REST request to update Charla : {}", charlaDTO);
        if (charlaDTO.getId() == null) {
            return createCharla(charlaDTO);
        }
        CharlaDTO result = charlaService.save(charlaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, charlaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /charlas : get all the charlas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of charlas in body
     */
    @GetMapping("/charlas")
    @Timed
    public ResponseEntity<List<CharlaDTO>> getAllCharlas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Charlas");
        Page<CharlaDTO> page = charlaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/charlas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /charlas/:id : get the "id" charla.
     *
     * @param id the id of the charlaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the charlaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/charlas/{id}")
    @Timed
    public ResponseEntity<CharlaDTO> getCharla(@PathVariable Long id) {
        log.debug("REST request to get Charla : {}", id);
        CharlaDTO charlaDTO = charlaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(charlaDTO));
    }

    /**
     * DELETE  /charlas/:id : delete the "id" charla.
     *
     * @param id the id of the charlaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/charlas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCharla(@PathVariable Long id) {
        log.debug("REST request to delete Charla : {}", id);
        charlaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
