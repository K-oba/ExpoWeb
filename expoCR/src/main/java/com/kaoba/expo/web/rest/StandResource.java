package com.kaoba.expo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoba.expo.service.StandService;
import com.kaoba.expo.web.rest.util.HeaderUtil;
import com.kaoba.expo.web.rest.util.PaginationUtil;
import com.kaoba.expo.service.dto.StandDTO;
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
 * REST controller for managing Stand.
 */
@RestController
@RequestMapping("/api")
public class StandResource {

    private final Logger log = LoggerFactory.getLogger(StandResource.class);

    private static final String ENTITY_NAME = "stand";

    private final StandService standService;

    public StandResource(StandService standService) {
        this.standService = standService;
    }

    /**
     * POST  /stands : Create a new stand.
     *
     * @param standDTO the standDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new standDTO, or with status 400 (Bad Request) if the stand has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stands")
    @Timed
    public ResponseEntity<StandDTO> createStand(@RequestBody StandDTO standDTO) throws URISyntaxException {
        log.debug("REST request to save Stand : {}", standDTO);
        if (standDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new stand cannot already have an ID")).body(null);
        }
        StandDTO result = standService.save(standDTO);
        return ResponseEntity.created(new URI("/api/stands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stands : Updates an existing stand.
     *
     * @param standDTO the standDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated standDTO,
     * or with status 400 (Bad Request) if the standDTO is not valid,
     * or with status 500 (Internal Server Error) if the standDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stands")
    @Timed
    public ResponseEntity<StandDTO> updateStand(@RequestBody StandDTO standDTO) throws URISyntaxException {
        log.debug("REST request to update Stand : {}", standDTO);
        if (standDTO.getId() == null) {
            return createStand(standDTO);
        }
        StandDTO result = standService.save(standDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, standDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stands : get all the stands.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of stands in body
     */
    @GetMapping("/stands")
    @Timed
    public ResponseEntity<List<StandDTO>> getAllStands(@ApiParam Pageable pageable, @RequestParam(required = false) String filter) {
        if ("usuario-is-null".equals(filter)) {
            log.debug("REST request to get all Stands where usuario is null");
            return new ResponseEntity<>(standService.findAllWhereUsuarioIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Stands");
        Page<StandDTO> page = standService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stands");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /stands/:id : get the "id" stand.
     *
     * @param id the id of the standDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the standDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stands/{id}")
    @Timed
    public ResponseEntity<StandDTO> getStand(@PathVariable Long id) {
        log.debug("REST request to get Stand : {}", id);
        StandDTO standDTO = standService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(standDTO));
    }

    /**
     * DELETE  /stands/:id : delete the "id" stand.
     *
     * @param id the id of the standDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stands/{id}")
    @Timed
    public ResponseEntity<Void> deleteStand(@PathVariable Long id) {
        log.debug("REST request to delete Stand : {}", id);
        standService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
