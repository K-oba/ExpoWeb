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
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
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

    private  static  final  long VISITANTE = 4;

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, MailService mailService,PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Save a usuario.
     *
     * @param usuarioDTO the entity to save
     * @return the persisted entity
     */
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        log.debug("Request to save Usuario : {}", usuarioDTO);

        usuarioDTO.setRolId(usuarioDTO.getRolId() != null ? usuarioDTO.getRolId() : VISITANTE);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        String encriptPass = passwordEncoder.encode(usuario.getClave());
        usuario.setClave(encriptPass);
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

    @Transactional(readOnly = true)
    public UsuarioDTO findByEmail(String email){
        log.debug("Request to get Usuario by email : {}", email);
        Usuario usuario = usuarioRepository.findByCorreo(email);
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
        String id = Long.toString(usuario.getId());
        String encriptedId = Encriptar(id);
        //Long cryptLong = Long.parseLong(encriptedId);
        //usuario.setId(cryptLong);
        usuario.setClave(encriptedId);
       
        if(usuario!=null){
            mailService.sendPasswordResetMail(usuario);
        }
        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDTO changePassword(UsuarioDTO usuariodto) {
         String idEncriptado = usuariodto.getNombre();
         String idDesencriptado = Desencriptar(idEncriptado);
         log.debug("ID DESENCRIPTADO : {}", idDesencriptado);
         Long cryptLong = Long.parseLong(idDesencriptado);
         log.debug("ID DESENCRIPTADO : {}", idDesencriptado);
            //Long id = usuariodto.getId();
            String password = usuariodto.getClave();
            
            Usuario usuario = usuarioMapper.toEntity(usuariodto);
            usuario = usuarioRepository.findOneWithEagerRelationships(cryptLong);
            usuario.setClave(passwordEncoder.encode(password));
            usuarioRepository.save(usuario);
            //log.debug("Correo del usuario : {}", usuario);
            return usuarioMapper.toDto(usuario);
    }
	public boolean checkUserPass(String clave, String clave2) {
		return passwordEncoder.matches(clave2, clave);
	}
    
    public  String Encriptar(String texto) {
 
        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
        String base64EncryptedString = "";
 
        try {
 
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("ASCII"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
 
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
 
            byte[] plainTextBytes = texto.getBytes("ASCII");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }
    public  String Desencriptar(String textoEncriptado){
        log.debug("METODO ECNRIPTACION : {}", textoEncriptado);
        String secretKey = "qualityinfosolutions"; //llave para desenciptar datos
        String base64EncryptedString = "";
 
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("ASCII"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("ASCII"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
 
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
 
            byte[] plainText = decipher.doFinal(message);
 
            base64EncryptedString = new String(plainText, "UTF-8");
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }
}
