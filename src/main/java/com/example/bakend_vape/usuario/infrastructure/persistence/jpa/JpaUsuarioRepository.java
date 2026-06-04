package com.example.bakend_vape.usuario.infrastructure.persistence.jpa;

import com.example.bakend_vape.usuario.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UsuarioEntity> findByEsVip(Boolean esVip);

    Long countByRolIdRol(Long rolId);

}
