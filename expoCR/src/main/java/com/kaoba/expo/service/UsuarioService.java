package com.kaoba.expo.service;

import com.kaoba.expo.domain.Usuario;
import com.kaoba.expo.repository.UsuarioRepository;
import com.kaoba.expo.service.dto.UsuarioDTO;
import com.kaoba.expo.service.mapper.UsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Usuario.
 */
@Service
@Transactional
public class UsuarioService {

    private final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;
    
    private final MailService mailService;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, MailService mailService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.mailService = mailService;
    }

    /**
     * Save a usuario.
     *
     * @param usuarioDTO the entity to save
     * @return the persisted entity
     */
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        log.debug("Request to save Usuario : {}", usuarioDTO);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuario);
    }

    /**
     *  Get all the usuarios.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Usuarios");
        return usuarioRepository.findAll(pageable)
            .map(usuarioMapper::toDto);
    }

    /**
     *  Get one usuario by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UsuarioDTO findOne(Long id) {
        log.debug("Request to get Usuario : {}", id);
        Usuario usuario = usuarioRepository.findOneWithEagerRelationships(id);
        return usuarioMapper.toDto(usuario);
    }

    /**
     *  Delete the  usuario by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Usuario : {}", id);
        usuarioRepository.delete(id);
    }
    
    /**
     *  Get one usuario by id.
     *
     *  @param email the email of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)

    public UsuarioDTO requestPasswordReset(String correo) {
        log.debug("Correo del usuario : {}", correo);
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        usuario.setCorreo("valeram96@gmail.com");
        log.debug("usuario a enviar correo : {}", usuario);
        if(usuario!=null){
            mailService.sendPasswordResetMail(usuario);
        }
        return usuarioMapper.toDto(usuario);
    }
    
}
