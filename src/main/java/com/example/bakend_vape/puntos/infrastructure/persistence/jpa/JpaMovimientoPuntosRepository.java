package com.example.bakend_vape.puntos.infrastructure.persistence.jpa;

import com.example.bakend_vape.puntos.infrastructure.persistence.entity.MovimientoPuntosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaMovimientoPuntosRepository extends JpaRepository<MovimientoPuntosEntity, Long> {

    List<MovimientoPuntosEntity> findByUsuarioIdUsuario(Long idUsuario);

}
