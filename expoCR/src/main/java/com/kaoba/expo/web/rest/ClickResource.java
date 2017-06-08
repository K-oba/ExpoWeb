package com.kaoba.expo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoba.expo.service.ClickService;
import com.kaoba.expo.web.rest.util.HeaderUtil;
import com.kaoba.expo.web.rest.util.PaginationUtil;
import com.kaoba.expo.service.dto.ClickDTO;
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
 * REST controller for managing Click.
 */
@RestController
@RequestMapping("/api")
public class ClickResource {

    private final Logger log = LoggerFactory.getLogger(ClickResource.class);

    private static final String ENTITY_NAME = "click";

    private final ClickService clickService;

    public ClickResource(ClickService clickService) {
        this.clickService = clickService;
    }

    /**
     * POST  /clicks : Create a new click.
     *
     * @param clickDTO the clickDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clickDTO, or with status 400 (Bad Request) if the click has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clicks")
    @Timed
    public ResponseEntity<ClickDTO> createClick(@RequestBody ClickDTO clickDTO) throws URISyntaxException {
        log.debug("REST request to save Click : {}", clickDTO);
        if (clickDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new click cannot already have an ID")).body(null);
        }
        ClickDTO result = clickService.save(clickDTO);
        return ResponseEntity.created(new URI("/api/clicks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clicks : Updates an existing click.
     *
     * @param clickDTO the clickDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clickDTO,
     * or with status 400 (Bad Request) if the clickDTO is not valid,
     * or with status 500 (Internal Server Error) if the clickDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clicks")
    @Timed
    public ResponseEntity<ClickDTO> updateClick(@RequestBody ClickDTO clickDTO) throws URISyntaxException {
        log.debug("REST request to update Click : {}", clickDTO);
        if (clickDTO.getId() == null) {
            return createClick(clickDTO);
        }
        ClickDTO result = clickService.save(clickDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clickDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clicks : get all the clicks.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of clicks in body
     */
    @GetMapping("/clicks")
    @Timed
    public ResponseEntity<List<ClickDTO>> getAllClicks(@ApiParam Pageable pageable, @RequestParam(required = false) String filter) {
        if ("stand-is-null".equals(filter)) {
            log.debug("REST request to get all Clicks where stand is null");
            return new ResponseEntity<>(clickService.findAllWhereStandIsNull(),
                    HttpStatus.OK);
        }
        if ("subcategoria-is-null".equals(filter)) {
            log.debug("REST request to get all Clicks where subCategoria is null");
            return new ResponseEntity<>(clickService.findAllWhereSubCategoriaIsNull(),
                    HttpStatus.OK);
        }
        if ("exposicion-is-null".equals(filter)) {
            log.debug("REST request to get all Clicks where exposicion is null");
            return new ResponseEntity<>(clickService.findAllWhereExposicionIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Clicks");
        Page<ClickDTO> page = clickService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clicks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clicks/:id : get the "id" click.
     *
     * @param id the id of the clickDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clickDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clicks/{id}")
    @Timed
    public ResponseEntity<ClickDTO> getClick(@PathVariable Long id) {
        log.debug("REST request to get Click : {}", id);
        ClickDTO clickDTO = clickService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clickDTO));
    }

    /**
     * DELETE  /clicks/:id : delete the "id" click.
     *
     * @param id the id of the clickDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clicks/{id}")
    @Timed
    public ResponseEntity<Void> deleteClick(@PathVariable Long id) {
        log.debug("REST request to delete Click : {}", id);
        clickService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
