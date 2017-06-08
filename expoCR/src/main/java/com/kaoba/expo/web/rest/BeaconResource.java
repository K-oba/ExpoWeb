package com.kaoba.expo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoba.expo.service.BeaconService;
import com.kaoba.expo.web.rest.util.HeaderUtil;
import com.kaoba.expo.web.rest.util.PaginationUtil;
import com.kaoba.expo.service.dto.BeaconDTO;
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
 * REST controller for managing Beacon.
 */
@RestController
@RequestMapping("/api")
public class BeaconResource {

    private final Logger log = LoggerFactory.getLogger(BeaconResource.class);

    private static final String ENTITY_NAME = "beacon";

    private final BeaconService beaconService;

    public BeaconResource(BeaconService beaconService) {
        this.beaconService = beaconService;
    }

    /**
     * POST  /beacons : Create a new beacon.
     *
     * @param beaconDTO the beaconDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new beaconDTO, or with status 400 (Bad Request) if the beacon has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/beacons")
    @Timed
    public ResponseEntity<BeaconDTO> createBeacon(@RequestBody BeaconDTO beaconDTO) throws URISyntaxException {
        log.debug("REST request to save Beacon : {}", beaconDTO);
        if (beaconDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new beacon cannot already have an ID")).body(null);
        }
        BeaconDTO result = beaconService.save(beaconDTO);
        return ResponseEntity.created(new URI("/api/beacons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /beacons : Updates an existing beacon.
     *
     * @param beaconDTO the beaconDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated beaconDTO,
     * or with status 400 (Bad Request) if the beaconDTO is not valid,
     * or with status 500 (Internal Server Error) if the beaconDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/beacons")
    @Timed
    public ResponseEntity<BeaconDTO> updateBeacon(@RequestBody BeaconDTO beaconDTO) throws URISyntaxException {
        log.debug("REST request to update Beacon : {}", beaconDTO);
        if (beaconDTO.getId() == null) {
            return createBeacon(beaconDTO);
        }
        BeaconDTO result = beaconService.save(beaconDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, beaconDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /beacons : get all the beacons.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of beacons in body
     */
    @GetMapping("/beacons")
    @Timed
    public ResponseEntity<List<BeaconDTO>> getAllBeacons(@ApiParam Pageable pageable, @RequestParam(required = false) String filter) {
        if ("stand-is-null".equals(filter)) {
            log.debug("REST request to get all Beacons where stand is null");
            return new ResponseEntity<>(beaconService.findAllWhereStandIsNull(),
                    HttpStatus.OK);
        }
        if ("subcategoria-is-null".equals(filter)) {
            log.debug("REST request to get all Beacons where subCategoria is null");
            return new ResponseEntity<>(beaconService.findAllWhereSubCategoriaIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Beacons");
        Page<BeaconDTO> page = beaconService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/beacons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /beacons/:id : get the "id" beacon.
     *
     * @param id the id of the beaconDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the beaconDTO, or with status 404 (Not Found)
     */
    @GetMapping("/beacons/{id}")
    @Timed
    public ResponseEntity<BeaconDTO> getBeacon(@PathVariable Long id) {
        log.debug("REST request to get Beacon : {}", id);
        BeaconDTO beaconDTO = beaconService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(beaconDTO));
    }

    /**
     * DELETE  /beacons/:id : delete the "id" beacon.
     *
     * @param id the id of the beaconDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/beacons/{id}")
    @Timed
    public ResponseEntity<Void> deleteBeacon(@PathVariable Long id) {
        log.debug("REST request to delete Beacon : {}", id);
        beaconService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
