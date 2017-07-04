package com.kaoba.expo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoba.expo.domain.Usuario;
import com.kaoba.expo.service.ExposicionService;
import com.kaoba.expo.service.MailService;
import com.kaoba.expo.service.UsuarioService;
import com.kaoba.expo.service.dto.InvitationDTO;
import com.kaoba.expo.service.dto.UsuarioDTO;
import com.kaoba.expo.service.mapper.UsuarioMapper;
import com.kaoba.expo.web.rest.util.HeaderUtil;
import com.kaoba.expo.web.rest.util.PaginationUtil;
import com.kaoba.expo.service.dto.ExposicionDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing Exposicion.
 */
@RestController
@RequestMapping("/api")
public class ExposicionResource {

    private final Logger log = LoggerFactory.getLogger(ExposicionResource.class);

    private static final String ENTITY_NAME = "exposicion";

    private final ExposicionService exposicionService;
    private final UsuarioService usuarioService;
    private final MailService mailService;
    @Autowired
    UsuarioMapper usuarioMapper;

    public ExposicionResource(ExposicionService exposicionService, UsuarioService usuarioService, MailService mailService) {
        this.exposicionService = exposicionService;
        this.usuarioService = usuarioService;
        this.mailService = mailService;
    }

    /**
     * POST  /exposicions : Create a new exposicion.
     *
     * @param exposicionDTO the exposicionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exposicionDTO, or with status 400 (Bad Request) if the exposicion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exposicions")
    @Timed
    public ResponseEntity<ExposicionDTO> createExposicion(@RequestBody ExposicionDTO exposicionDTO) throws URISyntaxException {
        log.debug("REST request to save Exposicion : {}", exposicionDTO);
        if (exposicionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new exposicion cannot already have an ID")).body(null);
        }
        ExposicionDTO result = exposicionService.save(exposicionDTO);
        return ResponseEntity.created(new URI("/api/exposicions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exposicions : Updates an existing exposicion.
     *
     * @param exposicionDTO the exposicionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exposicionDTO,
     * or with status 400 (Bad Request) if the exposicionDTO is not valid,
     * or with status 500 (Internal Server Error) if the exposicionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exposicions")
    @Timed
    public ResponseEntity<ExposicionDTO> updateExposicion(@RequestBody ExposicionDTO exposicionDTO) throws URISyntaxException {
        log.debug("REST request to update Exposicion : {}", exposicionDTO);
        if (exposicionDTO.getId() == null) {
            return createExposicion(exposicionDTO);
        }
        ExposicionDTO result = exposicionService.save(exposicionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, exposicionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exposicions : get all the exposicions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of exposicions in body
     */
    @GetMapping("/exposicions")
    @Timed
    public ResponseEntity<List<ExposicionDTO>> getAllExposicions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Exposicions");
        Page<ExposicionDTO> page = exposicionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exposicions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }    /**
     * GET  /exposicions : get all the live exposicions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of live exposicions in body
     */
    @GetMapping("/liveExposicions")
    @Timed
    public ResponseEntity<List<ExposicionDTO>> getLiveExposicions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of live Exposicions");
        Page<ExposicionDTO> page = exposicionService.findLive(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exposicions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exposicions/:id : get the "id" exposicion.
     *
     * @param id the id of the exposicionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exposicionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exposicions/{id}")
    @Timed
    public ResponseEntity<ExposicionDTO> getExposicion(@PathVariable Long id) {
        log.debug("REST request to get Exposicion : {}", id);
        ExposicionDTO exposicionDTO = exposicionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(exposicionDTO));
    }

    /**
     * DELETE  /exposicions/:id : delete the "id" exposicion.
     *
     * @param id the id of the exposicionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exposicions/{id}")
    @Timed
    public ResponseEntity<Void> deleteExposicion(@PathVariable Long id) {
        log.debug("REST request to delete Exposicion : {}", id);
        exposicionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * POST  /exposicions/:id : send the invite from "id" exposicion.
     *
     * @param invitationDTO the dto of the invitation to send
     */
    @PutMapping("sendInvite/id")
    @Timed
    public void sendInvitation(InvitationDTO invitationDTO) {
        log.debug("REST request to send invitation : {}", invitationDTO);
        ExposicionDTO exposicionDTO = this.exposicionService.findOne(invitationDTO.getExposicionId());
        UsuarioDTO usuarioDTO = this.usuarioService.findByEmail(invitationDTO.getEmail());
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        this.mailService.sendInvitationEmail(usuario, exposicionDTO);
    }
}
