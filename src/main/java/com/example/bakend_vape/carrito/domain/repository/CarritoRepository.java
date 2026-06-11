package com.example.bakend_vape.carrito.domain.repository;

import com.example.bakend_vape.carrito.domain.model.Carrito;

import java.util.Optional;

public interface CarritoRepository {

    Carrito save(Carrito carrito);

    Optional<Carrito> findById(Long id);

    /** Para usuarios autenticados */
    Optional<Carrito> findByUsuarioId(Long usuarioId);

    /** Para visitantes anónimos */
    Optional<Carrito> findBySessionId(String sessionId);

    void deleteById(Long id);
}
