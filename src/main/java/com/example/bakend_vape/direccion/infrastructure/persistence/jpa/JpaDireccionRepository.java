package com.example.bakend_vape.direccion.infrastructure.persistence.jpa;

import com.example.bakend_vape.direccion.infrastructure.persistence.entity.DireccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaDireccionRepository extends JpaRepository<DireccionEntity, Long> {

    List<DireccionEntity> findByUsuarioIdUsuario(Long usuarioId);

    Long countByUsuarioIdUsuario(Long usuarioId);

}
