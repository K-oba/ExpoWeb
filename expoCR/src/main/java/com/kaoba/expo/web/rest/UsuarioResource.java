package com.kaoba.expo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kaoba.expo.service.UsuarioService;
import com.kaoba.expo.web.rest.util.HeaderUtil;
import com.kaoba.expo.web.rest.util.PaginationUtil;
import com.kaoba.expo.service.dto.UsuarioDTO;
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
import com.kaoba.expo.service.MailService;
import com.kaoba.expo.web.rest.vm.ManagedUserVM;
import org.apache.commons.lang3.StringUtils;
/**
 * REST controller for managing Usuario.
 */
@RestController
@RequestMapping("/api")
public class UsuarioResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioResource.class);

    private static final String ENTITY_NAME = "usuario";
    private static final String EMAIL_EXISTS = "El correo electronico ya esta en uso.";

    private final UsuarioService usuarioService;
    
    private final MailService mailService;

    public UsuarioResource(UsuarioService usuarioService, MailService mailService) {
        this.usuarioService = usuarioService;
        this.mailService = mailService;
    }

    /**
     * POST  /usuarios : Create a new usuario.
     *
     * @param usuarioDTO the usuarioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new usuarioDTO, or with status 400 (Bad Request) if the usuario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/usuarios")
    @Timed
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody UsuarioDTO usuarioDTO) throws URISyntaxException {
        log.debug("REST request to save Usuario : {}", usuarioDTO);
        if (usuarioDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new usuario cannot already have an ID")).body(null);
        }else if(usuarioService.findByEmail(usuarioDTO.getCorreo()) != null){
            return  ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", EMAIL_EXISTS))
                    .body(null);
        }else{
            UsuarioDTO result = usuarioService.save(usuarioDTO);
            return ResponseEntity.created(new URI("/api/usuarios/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        }
    }

    /**
     * PUT  /usuarios : Updates an existing usuario.
     *
     * @param usuarioDTO the usuarioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated usuarioDTO,
     * or with status 400 (Bad Request) if the usuarioDTO is not valid,
     * or with status 500 (Internal Server Error) if the usuarioDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/usuarios")
    @Timed
    public ResponseEntity<UsuarioDTO> updateUsuario(@RequestBody UsuarioDTO usuarioDTO) throws URISyntaxException {
        log.debug("REST request to update Usuario : {}", usuarioDTO);
        if (usuarioDTO.getId() == null) {
            return createUsuario(usuarioDTO);
        }
        UsuarioDTO result = usuarioService.save(usuarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, usuarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /usuarios : get all the usuarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of usuarios in body
     */
    @GetMapping("/usuarios")
    @Timed
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Usuarios");
        Page<UsuarioDTO> page = usuarioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/usuarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /usuarios/:id : get the "id" usuario.
     *
     * @param id the id of the usuarioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the usuarioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/usuarios/{id}")
    @Timed
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable Long id) {
        log.debug("REST request to get Usuario : {}", id);
        UsuarioDTO usuarioDTO = usuarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(usuarioDTO));
    }

//    /**
//     * DELETE  /usuarios/:id : delete the "id" usuario.
//     *
//     * @param id the id of the usuarioDTO to delete
//     * @return the ResponseEntity with status 200 (OK)
//     */
//    @DeleteMapping("/usuarios/{id}")
//    @Timed
//    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
//        log.debug("REST request to delete Usuario : {}", id);
//        usuarioService.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
//    }
        /**
     * GET  /usuarios/:email : find user by id.
     *
     * @param email the id of the usuarioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/requestPasswordReset")
    @Timed
    public ResponseEntity<UsuarioDTO> requestPasswordReset(@RequestBody String email) {
        log.debug("Email desde el post : {}",email);
        UsuarioDTO usuario =  usuarioService.requestPasswordReset(email);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(usuario));
        //return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
     /**
     * POST  /account/changePassword : change the current user's password
     *
     * @param password the new password
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) if the new password is not strong enough
     */
    @PostMapping(path = "/changePassword")
    @Timed
            
    public ResponseEntity changePassword(@RequestBody UsuarioDTO usuario) {
        if (!checkPasswordLength(usuario.getClave())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }else{
            UsuarioDTO usuarioRequest = usuarioService.changePassword(usuario);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(usuarioRequest));
        }
        //usuarioService.changePassword(password);
        //return new ResponseEntity<>(HttpStatus.OK);
    }
    
    private boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}
