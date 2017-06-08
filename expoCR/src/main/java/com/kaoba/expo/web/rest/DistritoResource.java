package com.kaoba.expo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoba.expo.service.DistritoService;
import com.kaoba.expo.web.rest.util.HeaderUtil;
import com.kaoba.expo.web.rest.util.PaginationUtil;
import com.kaoba.expo.service.dto.DistritoDTO;
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
 * REST controller for managing Distrito.
 */
@RestController
@RequestMapping("/api")
public class DistritoResource {

    private final Logger log = LoggerFactory.getLogger(DistritoResource.class);

    private static final String ENTITY_NAME = "distrito";

    private final DistritoService distritoService;

    public DistritoResource(DistritoService distritoService) {
        this.distritoService = distritoService;
    }

    /**
     * POST  /distritos : Create a new distrito.
     *
     * @param distritoDTO the distritoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new distritoDTO, or with status 400 (Bad Request) if the distrito has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/distritos")
    @Timed
    public ResponseEntity<DistritoDTO> createDistrito(@RequestBody DistritoDTO distritoDTO) throws URISyntaxException {
        log.debug("REST request to save Distrito : {}", distritoDTO);
        if (distritoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new distrito cannot already have an ID")).body(null);
        }
        DistritoDTO result = distritoService.save(distritoDTO);
        return ResponseEntity.created(new URI("/api/distritos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /distritos : Updates an existing distrito.
     *
     * @param distritoDTO the distritoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated distritoDTO,
     * or with status 400 (Bad Request) if the distritoDTO is not valid,
     * or with status 500 (Internal Server Error) if the distritoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/distritos")
    @Timed
    public ResponseEntity<DistritoDTO> updateDistrito(@RequestBody DistritoDTO distritoDTO) throws URISyntaxException {
        log.debug("REST request to update Distrito : {}", distritoDTO);
        if (distritoDTO.getId() == null) {
            return createDistrito(distritoDTO);
        }
        DistritoDTO result = distritoService.save(distritoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, distritoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /distritos : get all the distritos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of distritos in body
     */
    @GetMapping("/distritos")
    @Timed
    public ResponseEntity<List<DistritoDTO>> getAllDistritos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Distritos");
        Page<DistritoDTO> page = distritoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/distritos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /distritos/:id : get the "id" distrito.
     *
     * @param id the id of the distritoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the distritoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/distritos/{id}")
    @Timed
    public ResponseEntity<DistritoDTO> getDistrito(@PathVariable Long id) {
        log.debug("REST request to get Distrito : {}", id);
        DistritoDTO distritoDTO = distritoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(distritoDTO));
    }

    /**
     * DELETE  /distritos/:id : delete the "id" distrito.
     *
     * @param id the id of the distritoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/distritos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDistrito(@PathVariable Long id) {
        log.debug("REST request to delete Distrito : {}", id);
        distritoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
