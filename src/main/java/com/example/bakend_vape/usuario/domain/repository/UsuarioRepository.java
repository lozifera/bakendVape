package com.example.bakend_vape.usuario.domain.repository;

import com.example.bakend_vape.usuario.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Usuario save(Usuario usuario);

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findAll();

    List<Usuario> findByEsVip(Boolean esVip);

    Long countByRolIdRol(Long rolId);

    void delete(Long id);

}