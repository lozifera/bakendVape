package com.example.bakend_vape.carrito.domain.repository;

import com.example.bakend_vape.carrito.domain.model.Carrito;

import java.util.Optional;

public interface CarritoRepository {

    Carrito save(Carrito carrito);

    Optional<Carrito> findById(Long id);

    Optional<Carrito> findByUsuarioId(Long usuarioId);

}
