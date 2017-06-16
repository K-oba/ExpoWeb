package com.kaoba.expo.repository;

import com.kaoba.expo.domain.Usuario;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Usuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    @Query("select distinct usuario from Usuario usuario left join fetch usuario.timelines")
    List<Usuario> findAllWithEagerRelationships();

    @Query("select usuario from Usuario usuario left join fetch usuario.timelines where usuario.id =:id")
    Usuario findOneWithEagerRelationships(@Param("id") Long id);

    Usuario findByCorreo(@Param("correo") String correo);

}
