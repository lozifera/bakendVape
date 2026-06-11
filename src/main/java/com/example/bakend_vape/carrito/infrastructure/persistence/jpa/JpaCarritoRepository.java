package com.example.bakend_vape.carrito.infrastructure.persistence.jpa;

import com.example.bakend_vape.carrito.infrastructure.persistence.entity.CarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCarritoRepository extends JpaRepository<CarritoEntity, Long> {

    Optional<CarritoEntity> findByUsuarioIdUsuario(Long idUsuario);

    Optional<CarritoEntity> findBySessionId(String sessionId);
}
