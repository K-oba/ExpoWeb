package com.kaoba.expo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoba.expo.service.HistorialUsuariosExpoService;
import com.kaoba.expo.web.rest.util.HeaderUtil;
import com.kaoba.expo.web.rest.util.PaginationUtil;
import com.kaoba.expo.service.dto.HistorialUsuariosExpoDTO;
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
 * REST controller for managing HistorialUsuariosExpo.
 */
@RestController
@RequestMapping("/api")
public class HistorialUsuariosExpoResource {

    private final Logger log = LoggerFactory.getLogger(HistorialUsuariosExpoResource.class);

    private static final String ENTITY_NAME = "historialUsuariosExpo";

    private final HistorialUsuariosExpoService historialUsuariosExpoService;

    public HistorialUsuariosExpoResource(HistorialUsuariosExpoService historialUsuariosExpoService) {
        this.historialUsuariosExpoService = historialUsuariosExpoService;
    }

    /**
     * POST  /historial-usuarios-expos : Create a new historialUsuariosExpo.
     *
     * @param historialUsuariosExpoDTO the historialUsuariosExpoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new historialUsuariosExpoDTO, or with status 400 (Bad Request) if the historialUsuariosExpo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/historial-usuarios-expos")
    @Timed
    public ResponseEntity<HistorialUsuariosExpoDTO> createHistorialUsuariosExpo(@RequestBody HistorialUsuariosExpoDTO historialUsuariosExpoDTO) throws URISyntaxException {
        log.debug("REST request to save HistorialUsuariosExpo : {}", historialUsuariosExpoDTO);
        if (historialUsuariosExpoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new historialUsuariosExpo cannot already have an ID")).body(null);
        }
        HistorialUsuariosExpoDTO result = historialUsuariosExpoService.save(historialUsuariosExpoDTO);
        return ResponseEntity.created(new URI("/api/historial-usuarios-expos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /historial-usuarios-expos : Updates an existing historialUsuariosExpo.
     *
     * @param historialUsuariosExpoDTO the historialUsuariosExpoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated historialUsuariosExpoDTO,
     * or with status 400 (Bad Request) if the historialUsuariosExpoDTO is not valid,
     * or with status 500 (Internal Server Error) if the historialUsuariosExpoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/historial-usuarios-expos")
    @Timed
    public ResponseEntity<HistorialUsuariosExpoDTO> updateHistorialUsuariosExpo(@RequestBody HistorialUsuariosExpoDTO historialUsuariosExpoDTO) throws URISyntaxException {
        log.debug("REST request to update HistorialUsuariosExpo : {}", historialUsuariosExpoDTO);
        if (historialUsuariosExpoDTO.getId() == null) {
            return createHistorialUsuariosExpo(historialUsuariosExpoDTO);
        }
        HistorialUsuariosExpoDTO result = historialUsuariosExpoService.save(historialUsuariosExpoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, historialUsuariosExpoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /historial-usuarios-expos : get all the historialUsuariosExpos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of historialUsuariosExpos in body
     */
    @GetMapping("/historial-usuarios-expos")
    @Timed
    public ResponseEntity<List<HistorialUsuariosExpoDTO>> getAllHistorialUsuariosExpos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of HistorialUsuariosExpos");
        Page<HistorialUsuariosExpoDTO> page = historialUsuariosExpoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/historial-usuarios-expos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /historial-usuarios-expos/:id : get the "id" historialUsuariosExpo.
     *
     * @param id the id of the historialUsuariosExpoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the historialUsuariosExpoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/historial-usuarios-expos/{id}")
    @Timed
    public ResponseEntity<HistorialUsuariosExpoDTO> getHistorialUsuariosExpo(@PathVariable Long id) {
        log.debug("REST request to get HistorialUsuariosExpo : {}", id);
        HistorialUsuariosExpoDTO historialUsuariosExpoDTO = historialUsuariosExpoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(historialUsuariosExpoDTO));
    }

    /**
     * DELETE  /historial-usuarios-expos/:id : delete the "id" historialUsuariosExpo.
     *
     * @param id the id of the historialUsuariosExpoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/historial-usuarios-expos/{id}")
    @Timed
    public ResponseEntity<Void> deleteHistorialUsuariosExpo(@PathVariable Long id) {
        log.debug("REST request to delete HistorialUsuariosExpo : {}", id);
        historialUsuariosExpoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
